package app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "booking_id")
    private String bookingId;

    @Column(name = "booking_amount")
    private BigDecimal bookingAmount;

    @Column(name = "gst_percentage")
    private BigDecimal gstPercentage;

    @Column(name = "gst_amount")
    private BigDecimal gstAmount;

    @Column(name = "convenience_fee")
    private BigDecimal convenienceFee;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
}