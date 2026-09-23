package app.mapper;

import org.springframework.stereotype.Component;


import app.dto.DiningEventResponse;
import app.entity.Event;
import app.util.LocationUtil;

@Component
public class DiningEventResponseMapper {

    public DiningEventResponse eventDiningResponse(Event event) {

        DiningEventResponse response = new DiningEventResponse();

        response.setEventName(event.getEventName());
        response.setEventLocation( LocationUtil.getLocality(event.getEventLocation()));
        response.setRating(event.getRating());
        response.setEventPrice(event.getEventPrice());
        response.setEventImage(event.getEventImage());
        response.setEventOffers(event.getEventOffers());
        response.setEventReview(event.getEventReview());
        response.setEventDescription(event.getEventDescription());

        return response;
    }
}
