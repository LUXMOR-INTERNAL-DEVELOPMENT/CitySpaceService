package app.dao;

import java.math.BigDecimal;

public interface PaymentDao {

    BigDecimal getBookingAmount(String bookingId);

    void savePayment(
            String paymentId,
            String bookingId,
            BigDecimal bookingAmount,
            BigDecimal gstPercentage,
            BigDecimal gstAmount,
            BigDecimal convenienceFee,
            BigDecimal totalAmount,
            String paymentMethod,
            String paymentStatus,
            String transactionId);
}