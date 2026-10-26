package app.dto;

import java.math.BigDecimal;

import lombok.Data;


@Data

public class EventResponse {
	
	private String eventName; 
	private String region;
	private double rating; 
	private BigDecimal price; 
	private String eventImage;
	public EventResponse(String eventName, String region, double rating, BigDecimal price,String eventImage) {
		
		this.eventName = eventName;
		this.region = region;
		this.rating = rating;
		this.price = price;
		this.eventImage=eventImage;
	}

}
