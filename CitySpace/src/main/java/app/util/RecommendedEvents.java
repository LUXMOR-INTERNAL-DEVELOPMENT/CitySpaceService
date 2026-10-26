
package app.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Event;

@Component
public class RecommendedEvents {

    @Autowired
    private GetNearByEvents getNearEvents;

    public List<Event> getRecommendedEvents(
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

        Map<String, Event> topEventsByCategory =
                new LinkedHashMap<>();

        LocalDate today = LocalDate.now();

        for (Event event : nearbyEvents) {

            // Only include events scheduled for today or later
            if (event.getEventDate() == null
                    || event.getEventDate().isBlank()) {
                continue;
            }

            LocalDate eventDate;

            try {
                eventDate = LocalDate.parse(event.getEventDate());
            } catch (DateTimeParseException e) {
                continue;
            }

            if (eventDate.isBefore(today)) {
                continue;
            }

            String categoryId = event.getCategoryId();

            if (categoryId == null || categoryId.isBlank()) {
                continue;
            }

            Event currentTopEvent =
                    topEventsByCategory.get(categoryId);

            long currentVisits =
                    currentTopEvent == null
                            || currentTopEvent.getMostVisited() == null
                            ? 0L
                            : currentTopEvent.getMostVisited();

            long eventVisits =
                    event.getMostVisited() == null
                            ? 0L
                            : event.getMostVisited();

            if (currentTopEvent == null
                    || eventVisits > currentVisits) {

                topEventsByCategory.put(categoryId, event);
            }
        }

        return new ArrayList<>(topEventsByCategory.values());
    }
}
