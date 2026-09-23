package app.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Event;
import app.entity.User;
import app.exception.UserIdNotFoundException;
import app.repository.UserRepository;


@Component
public class GetNearByEvents {
	@Autowired
    private UserRepository userRepository;

    public List<Event> getNearbyEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

	    List<Event> events =
	            userRepository.getActiveEvents();

	    List<Event> nearbyEvents =
	            new ArrayList<>();

        // 1. Latitude and longitude
        if (latitude != null && longitude != null) {

            for (Event event : events) {

                double distance =
                        DistanceCalculator.calculateDistance(
                                latitude,
                                longitude,
                                event.getLatitude(),
                                event.getLongitude()
                        );

                if (distance <= radius) {
                    nearbyEvents.add(event);
                }
            }
        }

	    // 2. Selected location
	    else if (location != null
	            && !location.trim().isEmpty()) {

	        for (Event event : events) {

	            if (event.getEventLocation() != null
	                    && event.getEventLocation()
	                            .toLowerCase()
	                            .contains(location.trim().toLowerCase())) {

                    nearbyEvents.add(event);
                }
            }
        }

        // 3. User's saved location
        else {

            User user =
                    userRepository.getUserById(userId);

            if (user == null) {
                throw new UserIdNotFoundException(
                        "User not found"
                );
            }

            String userLocation =
                    user.getUserLocation();

            for (Event event : events) {

	            if (userLocation != null
	                    && event.getEventLocation() != null
	                    && event.getEventLocation()
	                            .toLowerCase()
	                            .contains(userLocation.trim().toLowerCase())) {

                    nearbyEvents.add(event);
                }
            }
        }

        return nearbyEvents;
    }
    
    public List<Event> getNearbyEventsByCategory(
            String categoryId,
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

        List<Event> events =
                userRepository.getActiveEvents();

        List<Event> categoryEvents =
                new ArrayList<>();


        // 1. Latitude and longitude
        if (latitude != null && longitude != null) {

            for (Event event : events) {

                // First check category
                if (!categoryId.equalsIgnoreCase(
                        event.getCategoryId())) {
                    continue;
                }

                double distance =
                        DistanceCalculator.calculateDistance(
                                latitude,
                                longitude,
                                event.getLatitude(),
                                event.getLongitude()
                        );

                if (distance <= radius) {
                    categoryEvents.add(event);
                }
            }
        }


        // 2. Selected location
        else if (location != null
                && !location.trim().isEmpty()) {

            for (Event event : events) {

                if (categoryId.equalsIgnoreCase(
                        event.getCategoryId())
                        && event.getEventLocation() != null
                        && event.getEventLocation()
                                .toLowerCase()
                                .contains(location.trim().toLowerCase())) {

                    categoryEvents.add(event);
                }
            }
        }


        // 3. User's saved location
        else {

            User user =
                    userRepository.getUserById(userId);

            if (user == null) {
                throw new UserIdNotFoundException(
                        "User not found"
                );
            }

            String userLocation =
                    user.getUserLocation();

            for (Event event : events) {

                if (categoryId.equalsIgnoreCase(
                        event.getCategoryId())
                        && userLocation != null
                        && event.getEventLocation() != null
                        && event.getEventLocation()
                                .toLowerCase()
                                .contains(userLocation.trim().toLowerCase())) {

                    categoryEvents.add(event);
                }
            }
        }

        return categoryEvents;
    }
}

