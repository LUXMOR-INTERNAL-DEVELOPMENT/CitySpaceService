package app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.Entity.Event;

@Component
public class WeekEndEvents {
	@Autowired
    private GetNearByEvents getNearEvents;

    public List<Event> getWeekendEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

        List<Event> nearbyEvents =
        		getNearEvents.getNearbyEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        Map<String, Event> topWeekendEvents =
                new HashMap<>();

        for (Event event : nearbyEvents) {

            String description =
                    event.getEventDescription();

            if (description == null) {
                continue;
            }

            String text =
                    description.toLowerCase();

            boolean isWeekendEvent =
                    text.contains("weekend")
                    || text.contains("enjoy")
                    || text.contains("outing")
                    || text.contains("fun");

            if (!isWeekendEvent) {
                continue;
            }

            String category =
                    event.getCategoryName();

            if (!topWeekendEvents.containsKey(category)
                    || event.getRating()
                    > topWeekendEvents
                            .get(category)
                            .getRating()) {

                topWeekendEvents.put(
                        category,
                        event
                );
            }
        }

        return new ArrayList<>(
                topWeekendEvents.values()
        );
    }
}
