package app.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.BookingDao;
import app.dao.DeliveryDetailsDao;
import app.dao.PaymentDao;
import app.dto.BookingCartItem;
import app.dto.DeliveryDetailsRequest;
import app.dto.DirectBookingProduct;
import app.dto.PaymentRequest;
import app.dto.PaymentResponse;
import app.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private DeliveryDetailsDao deliveryDetailsDao;

    @Override
    @Transactional
    public PaymentResponse makePayment(PaymentRequest request) {

        BigDecimal bookingAmount = BigDecimal.ZERO;

        List<BookingCartItem> cartItems = null;
        DirectBookingProduct product = null;

        // -------------------------------------------------
        // 1. DIRECT / CART CHECKOUT
        // -------------------------------------------------

        if ("DIRECT".equalsIgnoreCase(request.getCheckoutType())) {

            // =========================
            // BOOK NOW
            // =========================

            if (request.getProductId() == null
                    || request.getProductId().isBlank()) {

                throw new RuntimeException(
                        "Product ID is required for direct checkout");
            }

            if (request.getQuantity() == null
                    || request.getQuantity() < 1) {

                throw new RuntimeException(
                        "Quantity must be at least 1");
            }

            product =
                    bookingDao.getProductForBooking(
                            request.getProductId());

            if (product == null) {
                throw new RuntimeException(
                        "Product not found");
            }

            if (request.getQuantity()
                    > product.getAvailableQuantity()) {

                throw new RuntimeException(
                        "Requested quantity is not available");
            }

            // Product price × quantity
            bookingAmount =
                    product.getProductPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            request.getQuantity()))
                            .setScale(
                                    2,
                                    RoundingMode.HALF_UP);

        } else if ("CART".equalsIgnoreCase(
                request.getCheckoutType())) {

            // =========================
            // ADD TO CART → CHECKOUT
            // =========================

            cartItems =
                    bookingDao.getCartItemsForBooking(
                            request.getUserId());

            if (cartItems == null
                    || cartItems.isEmpty()) {

                throw new RuntimeException(
                        "Cart is empty");
            }

            for (BookingCartItem item : cartItems) {

                BigDecimal itemTotal =
                        item.getProductPrice()
                                .multiply(
                                        BigDecimal.valueOf(
                                                item.getQuantity()));

                bookingAmount =
                        bookingAmount.add(itemTotal);
            }

            bookingAmount =
                    bookingAmount.setScale(
                            2,
                            RoundingMode.HALF_UP);

        } else {

            throw new RuntimeException(
                    "Invalid checkout type. Use DIRECT or CART");
        }

        // -------------------------------------------------
        // 2. GST CALCULATION
        // -------------------------------------------------

        BigDecimal gstPercentage =
                BigDecimal.valueOf(18);

        BigDecimal gstAmount =
                bookingAmount
                        .multiply(gstPercentage)
                        .divide(
                                BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP);

        // -------------------------------------------------
        // 3. CONVENIENCE FEE
        // -------------------------------------------------

        BigDecimal convenienceFee =
                BigDecimal.valueOf(50);

        // -------------------------------------------------
        // 4. FINAL AMOUNT
        // -------------------------------------------------

        BigDecimal totalAmount =
                bookingAmount
                        .add(gstAmount)
                        .add(convenienceFee)
                        .setScale(
                                2,
                                RoundingMode.HALF_UP);

        // -------------------------------------------------
        // 5. GENERATE PAYMENT DETAILS
        // -------------------------------------------------

        String paymentId =
                "PAY-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        String bookingId =
                "BOOK-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        String transactionId =
                "TXN-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        // For our current simple flow,
        // payment is considered successful immediately.
        String paymentStatus = "SUCCESS";

        // -------------------------------------------------
        // 6. SAVE PAYMENT
        // -------------------------------------------------

        paymentDao.savePayment(
                paymentId,
                bookingId,
                bookingAmount,
                gstPercentage,
                gstAmount,
                convenienceFee,
                totalAmount,
                request.getPaymentMethod(),
                paymentStatus,
                transactionId
        );

        // -------------------------------------------------
        // 7. CREATE PARENT BOOKING
        // -------------------------------------------------

        bookingDao.createBooking(
                bookingId,
                request.getUserId(),
                totalAmount
        );

        // -------------------------------------------------
        // 8. CREATE BOOKING ITEM(S)
        // -------------------------------------------------

        if ("DIRECT".equalsIgnoreCase(
                request.getCheckoutType())) {

            // -------------------------
            // DIRECT BOOKING
            // -------------------------

            BigDecimal productTotal =
                    product.getProductPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            request.getQuantity()))
                            .setScale(
                                    2,
                                    RoundingMode.HALF_UP);

            String bookingItemId =
                    "ITEM-" +
                    UUID.randomUUID()
                            .toString()
                            .substring(0, 8)
                            .toUpperCase();

            bookingDao.createBookingItem(
                    bookingItemId,
                    bookingId,
                    product.getProductId(),
                    request.getQuantity(),
                    product.getProductPrice(),
                    productTotal,
                    request.getUserId()
            );

        } else {

            // -------------------------
            // CART BOOKING
            // -------------------------

            for (BookingCartItem item : cartItems) {

                BigDecimal itemTotal =
                        item.getProductPrice()
                                .multiply(
                                        BigDecimal.valueOf(
                                                item.getQuantity()))
                                .setScale(
                                        2,
                                        RoundingMode.HALF_UP);

                String bookingItemId =
                        "ITEM-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 8)
                                .toUpperCase();

                bookingDao.createBookingItem(
                        bookingItemId,
                        bookingId,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getProductPrice(),
                        itemTotal,
                        request.getUserId()
                );
            }
        }

        // -------------------------------------------------
        // 9. SAVE DELIVERY DETAILS
        // -------------------------------------------------

        DeliveryDetailsRequest deliveryRequest =
                new DeliveryDetailsRequest();

        deliveryRequest.setBookingId(bookingId);

        deliveryRequest.setRecipientName(
                request.getRecipientName());

        deliveryRequest.setPhoneNumber(
                request.getPhoneNumber());

        deliveryRequest.setAddress(
                request.getAddress());

        deliveryRequest.setCity(
                request.getCity());

        deliveryRequest.setState(
                request.getState());

        deliveryRequest.setPincode(
                request.getPincode());

        deliveryDetailsDao.saveDeliveryDetails(
                deliveryRequest,
                request.getUserId()
        );

        // -------------------------------------------------
        // 10. CLEAR CART
        // -------------------------------------------------

        if ("CART".equalsIgnoreCase(
                request.getCheckoutType())) {

            bookingDao.clearCart(
                    request.getUserId());
        }

        // -------------------------------------------------
        // 11. PREPARE RESPONSE
        // -------------------------------------------------

        PaymentResponse response =
                new PaymentResponse();

        response.setPaymentId(paymentId);

        response.setBookingId(bookingId);

        response.setBookingAmount(
                bookingAmount);

        response.setGstPercentage(
                gstPercentage);

        response.setGstAmount(
                gstAmount);

        response.setConvenienceFee(
                convenienceFee);

        response.setTotalAmount(
                totalAmount);

        response.setPaymentMethod(
                request.getPaymentMethod());

        response.setPaymentStatus(
                paymentStatus);

        response.setTransactionId(
                transactionId);

        response.setPaymentDate(
                LocalDateTime.now());

        response.setMessage(
                "Payment successful and booking confirmed");

        return response;
    }
}