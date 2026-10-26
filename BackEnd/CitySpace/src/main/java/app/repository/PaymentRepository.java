package app.repository;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${store.savePayment}")
    private String savePayment;
    
    @Value("${store.updatePayment}")
    private String updatePayment;


    public void savePayment(
            String paymentId,
            String bookingId,
            BigDecimal bookingAmount,
            BigDecimal gstPercentage,
            BigDecimal gstAmount,
            BigDecimal convenienceFee,
            BigDecimal totalAmount,
            String paymentMethod,
            String paymentStatus,
            String transactionId) {

        jdbcTemplate.update(
                savePayment,
                paymentId,
                bookingId,
                bookingAmount,
                gstPercentage,
                gstAmount,
                convenienceFee,
                totalAmount,
                paymentMethod,
                paymentStatus,
                transactionId
        );
    }
    
    public void updatePayment(
            String paymentId,
            String bookingId,
            String paymentStatus,
            String transactionId) {

        jdbcTemplate.update(
                updatePayment,
                bookingId,
                paymentStatus,
                transactionId,
                paymentId
        );
    }
}