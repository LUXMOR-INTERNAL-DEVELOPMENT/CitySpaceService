package app.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import app.Entity.Event;
import app.dto.EventFilterResponse;

@Component
public class EventResponseFilterMapper {
   
	
	public List<EventFilterResponse>response(List<Event> events){
		return events.stream()
		        .map(event -> new EventFilterResponse(
		                event.getCategoryName(),
		                event.getEventDate(),
		                event.getEventPrice(),
		                event.getEventLocation(),
		                event.getRating()
		        ))
		        .toList();
	}
}
