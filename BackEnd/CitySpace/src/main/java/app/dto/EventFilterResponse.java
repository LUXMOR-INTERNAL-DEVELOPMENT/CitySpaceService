package app.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class EventFilterResponse {
	private String categoryName;
	private String eventDate; 
	private BigDecimal eventPrice; 
	private String eventLocation; 
	private double rating; 
	
	public EventFilterResponse(String categoryName, String eventDate, BigDecimal eventPrice, String eventLocation, double rating) { 
		this.categoryName = categoryName; 
		this.eventDate = eventDate; 
		this.eventPrice = eventPrice; 
		this.eventLocation = eventLocation; 
		this.rating = rating; 
	}

	
	
}
