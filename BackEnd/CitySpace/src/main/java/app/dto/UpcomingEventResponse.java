package app.dto;

import java.math.BigDecimal;
import java.util.List;


import lombok.Data;


@Data
public class UpcomingEventResponse {
	
	private String eventName;
	private String eventLocation;
	private List<String> eventImages;
	private double rating;
	private BigDecimal eventPrice;
	private String eventDate;
	private String eventDay;
	private String eventDescription;  
	
	
	public UpcomingEventResponse(String eventName, String region, double rating, BigDecimal price,List<String> eventImages,String eventDate,String eventDay, String eventDescription) {

		this.eventName = eventName;
		this.eventLocation = region;
		this.rating = rating;
		this.eventPrice = price;
		this.eventImages=eventImages;
		this.eventDate=eventDate;
		this.eventDay=eventDay;
		this.eventDescription=eventDescription;
	}
		 
}
