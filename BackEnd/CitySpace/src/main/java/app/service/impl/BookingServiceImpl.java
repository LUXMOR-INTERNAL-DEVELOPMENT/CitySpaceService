package app.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.BookingDao;
import app.dto.BookingCartItem;
import app.dto.BookingResponse;
import app.dto.DirectBookingProduct;
import app.dto.DirectBookingRequest;
import app.exception.InsufficientQuantityException;
import app.exception.ResourceNotFoundException;
import app.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    // Existing Cart Checkout
    @Override
    @Transactional
    public void createBooking(String userId) {

        List<BookingCartItem> cartItems =
                bookingDao.getCartItemsForBooking(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        String bookingId =
                "BOOK-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        BigDecimal bookingTotal = BigDecimal.ZERO;

        for (BookingCartItem item : cartItems) {

            BigDecimal itemTotal =
                    item.getProductPrice()
                            .multiply(
                                    BigDecimal.valueOf(item.getQuantity()));

            bookingTotal = bookingTotal.add(itemTotal);
        }

        bookingDao.createBooking(
                bookingId,
                userId,
                bookingTotal);

        for (BookingCartItem item : cartItems) {

            BigDecimal itemTotal =
                    item.getProductPrice()
                            .multiply(
                                    BigDecimal.valueOf(item.getQuantity()));

            String bookingItemId =
                    UUID.randomUUID().toString();

            bookingDao.createBookingItem(
                    bookingItemId,
                    bookingId,
                    item.getProductId(),
                    item.getQuantity(),
                    item.getProductPrice(),
                    itemTotal,
                    userId);
        }

        bookingDao.clearCart(userId);
    }


    // Direct Book Now
    @Override
    @Transactional
    public BookingResponse createDirectBooking(
            DirectBookingRequest request) {

        DirectBookingProduct product;

        try {

            product =
                    bookingDao.getProductForBooking(
                            request.getProductId());

        } catch (EmptyResultDataAccessException e) {

            throw new ResourceNotFoundException(
                    "Product not found");
        }

        if (product == null) {

            throw new ResourceNotFoundException(
                    "Product not found");
        }

        // Check available quantity
        if (request.getQuantity()
                > product.getAvailableQuantity()) {

            throw new InsufficientQuantityException(
                    "Requested quantity is not available");
        }

        // Calculate booking amount
        BigDecimal unitPrice =
                product.getProductPrice();

        BigDecimal totalPrice =
                unitPrice.multiply(
                        BigDecimal.valueOf(
                                request.getQuantity()));

        // Generate booking ID
        String bookingId =
                "BOOK-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        // Create parent booking
        bookingDao.createBooking(
                bookingId,
                request.getUserId(),
                totalPrice);

        // Generate booking item ID
        String bookingItemId =
                UUID.randomUUID().toString();

        // Create booking item
        bookingDao.createBookingItem(
                bookingItemId,
                bookingId,
                request.getProductId(),
                request.getQuantity(),
                unitPrice,
                totalPrice,
                request.getUserId());

        // Prepare response
        BookingResponse response =
                new BookingResponse();

        response.setBookingId(bookingId);
       
        response.setTotalAmount(totalPrice);
        response.setBookingStatus("PENDING");
        response.setBookingDate(
                LocalDateTime.now());

        return response;
    }
}