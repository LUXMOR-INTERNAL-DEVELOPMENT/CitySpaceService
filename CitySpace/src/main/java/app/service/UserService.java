package app.service;

import java.util.List;


import app.dto.DiningBookingRequest;
import app.dto.DiningBookingResponse;
import app.dto.DiningEventResponse;
import app.dto.EvenFilterRequest;
import app.dto.EventFilterResponse;
import app.dto.EventResponse;
import app.dto.StatusUpdate;
import app.dto.UserResponse;
import app.dto.UserUpdateProfileRequest;
import app.entity.Event;


public interface UserService {

    List<Event> getAllEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius);

    List<EventResponse> TopPopularityEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius);

    List<EventResponse> weekEndEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius);

    UserResponse getUser(String user_id);

    String updateProfileById(
            String user_id,
            String role,
            UserUpdateProfileRequest userProfile);

    String updateStatus(
            String user_id,
            String role,
            StatusUpdate status);

    List<EventFilterResponse> getFilteredEvents(EvenFilterRequest filter);
    
    List<EventResponse> getEventsByCategory(String categoryId, String userId, Double latitude, Double longitude,
			String location, double radius);
    
    public DiningEventResponse getDiningEvent(String eventId);
    
    public DiningBookingResponse bookingDining(String userId, String eventId, DiningBookingRequest request);
}
