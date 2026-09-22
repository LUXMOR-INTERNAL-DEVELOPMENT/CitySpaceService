package app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
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
}