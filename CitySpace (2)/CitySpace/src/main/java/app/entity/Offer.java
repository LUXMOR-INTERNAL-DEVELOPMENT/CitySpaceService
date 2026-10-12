package app.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "offer")
public class Offer {

    @Id
    @Column(name = "id")
    private String offerId;

    private String eventId;

    private String offerText;

    private String offerDescription;

    private LocalTime startTime;

    private LocalTime endTime;

    private Boolean validAllDay;

    private Integer slotsLeft;

    private Boolean bookingRequired;

    private String status;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;
}