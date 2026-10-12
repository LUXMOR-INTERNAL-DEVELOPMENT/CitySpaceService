package app.dao;

import java.math.BigDecimal;

public interface PaymentDao {

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
            String transactionId
    );
    
    void updatePayment(
            String paymentId,
            String bookingId,
            String paymentStatus,
            String transactionId
    );
}