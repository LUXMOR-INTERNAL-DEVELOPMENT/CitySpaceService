package app.dao.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.dao.PaymentDao;
import app.repository.PaymentRepository;

@Repository
public class PaymentDaoImpl implements PaymentDao {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
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

        paymentRepository.savePayment(
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
    
    @Override
    public void updatePayment(
            String paymentId,
            String bookingId,
            String paymentStatus,
            String transactionId) {

        paymentRepository.updatePayment(
                paymentId,
                bookingId,
                paymentStatus,
                transactionId
        );
    }
}