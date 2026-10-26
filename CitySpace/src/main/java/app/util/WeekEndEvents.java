package app.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Event;

@Component
public class WeekEndEvents {


@Autowired
private GetNearByEvents getNearEvents;

@Autowired
private RatingCalculation ratingCalculation;

public List<Event> getWeekendEvents(
        String userId,
        Double latitude,
        Double longitude,
        String location,
        double radius) {

    // Get nearby events using existing location logic
    List<Event> nearbyEvents =
            getNearEvents.getNearbyEvents(
                    userId,
                    latitude,
                    longitude,
                    location,
                    radius
            );

    LocalDate today = LocalDate.now();
    LocalDate saturday;
    LocalDate sunday;

    // Sunday: show today's events only
    if (today.getDayOfWeek() == DayOfWeek.SUNDAY) {
        saturday = today;
        sunday = today;
    } else {
        // Find this week's Saturday and Sunday
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        saturday = monday.plusDays(5);
        sunday = monday.plusDays(6);
    }

    // Highest-rated weekend event per category
    Map<String, Event> topWeekendEvents = new HashMap<>();
    Map<String, Double> topRatingsByCategory = new HashMap<>();

    for (Event event : nearbyEvents) {

        // Skip events without a date
        if (event.getEventDate() == null
                || event.getEventDate().isBlank()) {
            continue;
        }

        // Parse the event date
        LocalDate eventDate =
                LocalDate.parse(event.getEventDate());

        // Skip events outside the required weekend
        if (eventDate.isBefore(saturday)
                || eventDate.isAfter(sunday)) {
            continue;
        }

        String category = event.getCategoryName();

        // Calculate average rating from the review table
        double averageRating =
                ratingCalculation.calculateEventRating(
                        event.getId()
                );

        // Keep the highest-rated event in each category
        if (!topWeekendEvents.containsKey(category)
                || averageRating
                > topRatingsByCategory.get(category)) {

            topWeekendEvents.put(category, event);
            topRatingsByCategory.put(
                    category,
                    averageRating
            );
        }
    }

    return new ArrayList<>(topWeekendEvents.values());
}


}


