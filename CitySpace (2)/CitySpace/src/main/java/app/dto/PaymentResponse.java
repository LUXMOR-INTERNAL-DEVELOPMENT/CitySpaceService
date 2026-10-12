package app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaymentResponse {

    private String paymentId;

    private String bookingId;

    private BigDecimal bookingAmount;

    private BigDecimal gstPercentage;

    private BigDecimal gstAmount;

    private BigDecimal convenienceFee;

    private BigDecimal totalAmount;

    private String paymentMethod;

    private String paymentStatus;

    private String transactionId;

    private LocalDateTime paymentDate;

    private String message;
}