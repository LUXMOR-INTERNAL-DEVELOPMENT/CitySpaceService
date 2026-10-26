package app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Event;

@Component
public class GetPopularityEvents {


@Autowired
private GetNearByEvents getNearEvents;

@Autowired
private RatingCalculation ratingCalculation;

public List<Event> getPopularityEvents(
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
            new HashMap<>();

    Map<String, Double> topRatingsByCategory =
            new HashMap<>();

    for (Event event : nearbyEvents) {

        String category = event.getCategoryName();

        double averageRating =
                ratingCalculation.calculateEventRating(
                        event.getId()
                );

        if (!topEventsByCategory.containsKey(category)
                || averageRating
                > topRatingsByCategory.get(category)) {

            topEventsByCategory.put(category, event);

            topRatingsByCategory.put(
                    category,
                    averageRating
            );
        }
    }

    return new ArrayList<>(
            topEventsByCategory.values()
    );
}


}
