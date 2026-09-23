package app.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiningEventResponse {

    private String eventName;
    private String eventLocation;
    private double rating;
    private BigDecimal eventPrice;
    private String eventImage;
    private String eventOffers;
    private double eventReview;
    private String eventDescription;
    
    public DiningEventResponse(
            String eventName,
            String eventLocation,
            double rating,
            BigDecimal eventPrice,
            String eventImage,
            String eventOffers,
            double eventReview) {

        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.rating = rating;
        this.eventPrice = eventPrice;
        this.eventImage = eventImage;
        this.eventOffers = eventOffers;
        this.eventReview = eventReview;
    }
}