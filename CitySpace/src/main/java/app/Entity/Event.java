package app.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="event")

public class Event {
	
	@Id
	@Column(name="event_id")
	private String eventId;
	private String vendorId;
	private String eventName; 
	private String eventDate; 
	private String eventLocation; 
	private String eventDescription; 
	private String eventImage;
	private String eventStatus;
	private double rating; 
	private BigDecimal eventPrice; 
	private double latitude; 
	private double longitude; 
	private String categoryId;
	private String categoryName;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime updatedAt;
	private String updatedBy;
}
