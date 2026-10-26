package app.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Event;
import app.entity.RestaurantDetails;
import app.repository.UserRepository;

@Component
public class GetEventsByCategory {

    @Autowired
    private GetNearByEvents getNearByEvents;

    @Autowired
    private UserRepository userRepository;

    public List<Event> getNearbyCategoryEvents(
            String categoryName,
            String subCategoryName,
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

        List<Event> nearbyEvents =
                getNearByEvents.getNearbyEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        List<Event> categoryEvents = new ArrayList<>();

        for (Event event : nearbyEvents) {

            // Filter by main category
            if (event.getCategoryName() == null
                    || categoryName == null
                    || !event.getCategoryName()
                            .equalsIgnoreCase(categoryName.trim())) {
                continue;
            }

            // No subcategory selected: return all matching category events
            if (subCategoryName == null
                    || subCategoryName.isBlank()) {

                categoryEvents.add(event);
                continue;
            }

            // Dining: filter using RestaurantDetails cuisine list
            if (categoryName.equalsIgnoreCase("Dining")) {

                RestaurantDetails restaurantDetails =
                        userRepository.getRestaurantDetails(
                                event.getId()
                        );

                if (restaurantDetails == null
                        || restaurantDetails.getCuisine() == null) {
                    continue;
                }

                boolean cuisineMatches =
                        restaurantDetails.getCuisine().stream()
                                .filter(cuisine -> cuisine != null)
                                .anyMatch(cuisine ->
                                        cuisine.trim().equalsIgnoreCase(
                                                subCategoryName.trim()
                                        )
                                );

                if (cuisineMatches) {
                    categoryEvents.add(event);
                }

            } else {

                // Preserve existing filtering for other categories
                String description = event.getEventDescription();

                if (description != null
                        && description.toLowerCase()
                                .contains(
                                        subCategoryName.trim()
                                                .toLowerCase()
                                )) {

                    categoryEvents.add(event);
                }
            }
        }

        return categoryEvents;
    }
}

