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

        List<Event> events = userRepository.getActiveEvents();

        List<Event> nearbyEvents = new ArrayList<>();

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

        // 2. Selected Location
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

        // 3. User's Saved Location
        else {

            User user = userRepository.getUserById(userId);

            if (user == null) {
                throw new UserIdNotFoundException(
                        "User not found"
                );
            }

            String userLocation = user.getUserLocation();

            if (userLocation == null
                    || userLocation.trim().isEmpty()) {
                return nearbyEvents;
            }

            for (Event event : events) {

                if (event.getEventLocation() != null
                        && event.getEventLocation()
                                .toLowerCase()
                                .contains(userLocation.trim().toLowerCase())) {

                    nearbyEvents.add(event);
                }
            }
        }

        return nearbyEvents;
    }
}


