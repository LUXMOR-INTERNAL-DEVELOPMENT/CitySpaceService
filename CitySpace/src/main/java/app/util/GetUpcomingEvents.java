package app.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Event;

@Component
public class GetUpcomingEvents {
	
	@Autowired
	private GetNearByEvents getNearByEvents;
	public List<Event> getUpcomingEvents(
	        String userId,
	        Double latitude,
	        Double longitude,
	        String location,
	        double radius) {

	    // Reuse existing location logic
	    List<Event> nearbyEvents =
	             getNearByEvents.getNearbyEvents(
	                    userId,
	                    latitude,
	                    longitude,
	                    location,
	                    radius
	            );

	    List<Event> upcomingEvents =
	            new ArrayList<>();

	    LocalDate today = LocalDate.now();
	    LocalDate oneMonthLater =
	            today.plusMonths(1);

	    for (Event event : nearbyEvents) {

	        if (event.getEventDate() == null) {
	            continue;
	        }

	        LocalDate eventDate =
	                LocalDate.parse(event.getEventDate());

	        if (!eventDate.isBefore(today)
	                && !eventDate.isAfter(oneMonthLater)) {

	            upcomingEvents.add(event);
	        }
	    }

	    return upcomingEvents;
	}
}
