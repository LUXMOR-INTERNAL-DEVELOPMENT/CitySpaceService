package app.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import app.dto.EventResponse;
import app.entity.Event;

@Component
public class EventResponseMapper {
	
	public List<EventResponse>eventResponse(List<Event>events){
	List<EventResponse>response=events.stream()
    .map(event -> new EventResponse(
            event.getEventName(),
            event.getEventLocation(),
            event.getRating(),
            event.getEventPrice(),
            event.getEventImage()
    )).toList();
	return response;
	}
}
