package app.service;

import java.util.List;

import app.Entity.Event;
import app.dto.EvenFilterRequest;
import app.dto.EventFilterResponse;
import app.dto.EventResponse;
import app.dto.StatusUpdate;
import app.dto.UserResponse;
import app.dto.UserUpdateProfileRequest;


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
}
