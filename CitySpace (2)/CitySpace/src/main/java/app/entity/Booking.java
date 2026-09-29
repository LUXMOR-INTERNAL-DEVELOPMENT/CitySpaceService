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
@Table(name = "store_booking")
public class Booking {

    @Id
    @Column(name = "booking_id")
    private String bookingId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "booking_status")
    private String bookingStatus;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}