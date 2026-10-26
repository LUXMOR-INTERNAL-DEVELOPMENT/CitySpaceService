package app.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dto.PaymentRequest;
import app.entity.Payment;

@Component
public class PaymentImplementation {
	 @Autowired
	  private PaymentValidator paymentValidator;

	  
	    public Payment processPayment(
	            String bookingId,
	            BigDecimal bookingAmount,
	            PaymentRequest request) {

	        // Validation is handled separately
	        paymentValidator.validate(request);

	        BigDecimal gstPercentage =
	                new BigDecimal("18");

	        BigDecimal gstAmount =
	                bookingAmount
	                        .multiply(gstPercentage)
	                        .divide(new BigDecimal("100"));

	        BigDecimal convenienceFee =
	                new BigDecimal("30");

	        BigDecimal totalAmount =
	                bookingAmount
	                        .add(gstAmount)
	                        .add(convenienceFee);
	        
	        String paymentId = "PAY-" + UUID.randomUUID()
	        .toString()
	        .substring(0, 8)
	        .toUpperCase();

	        Payment payment = new Payment();

	        payment.setPaymentId(
	                paymentId);

	        payment.setBookingId(bookingId);

	        payment.setBookingAmount(bookingAmount);

	        payment.setGstPercentage(gstPercentage);

	        payment.setGstAmount(gstAmount);

	        payment.setConvenienceFee(convenienceFee);

	        payment.setTotalAmount(totalAmount);

	        payment.setPaymentMethod(
	                request.getPaymentMethod());

	        payment.setPaymentStatus(
	                getPaymentStatus(
	                        request.getPaymentMethod()));

	        payment.setTransactionId(
	                "TXN-" + UUID.randomUUID()
	                        .toString()
	                        .substring(0, 8)
	                        .toUpperCase());

	        payment.setPaymentDate(
	                LocalDateTime.now());

	        return payment;
	    }

	    private String getPaymentStatus(
	            String paymentMethod) {

	        if ("COD".equalsIgnoreCase(paymentMethod)) {
	            return "PENDING";
	        }

	        return "SUCCESS";
	    }
}
