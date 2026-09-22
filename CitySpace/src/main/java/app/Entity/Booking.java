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
@Table(name = "booking")
public class Booking {

    @Id
    @Column(name = "booking_id")
    private String bookingId;

    private String userId;
    private String Id;
    private String vendorId;

    private String bookingDate;
    private String slotId;

    private int numberOfGuests;

    private BigDecimal totalAmount;
    private String bookingStatus;

    private LocalDateTime createdAt;
    private String createdBy;

    private LocalDateTime updatedAt;
    private String updatedBy;
}