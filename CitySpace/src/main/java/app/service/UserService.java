package app.service;

import java.util.List;

import app.dto.CategoryPageResponse;
import app.dto.DiningEventResponse;

import app.dto.EvenFilterRequest;
import app.dto.EventFilterResponse;
import app.dto.EventResponse;
import app.dto.HomeResponse;
import app.dto.ReviewRequest;
import app.dto.StatusUpdate;

import app.dto.UserResponse;
import app.dto.UserUpdateProfileRequest;
import jakarta.validation.Valid;



public interface UserService {
	
	HomeResponse getHomeEvents(
	        String userId,
	        Double latitude,
	        Double longitude,
	        String location,
	        double radius
	);

//	List<EventResponse> getAllEvents(
//            String userId,
//            Double latitude,
//            Double longitude,
//            String location,
//            double radius);
//	 List<UpcomingEventResponse> getUpcomingEvents(
//	            String userId,
//	            Double latitude,
//	            Double longitude,
//	            String location,
//	            Double radius);
//    
//    List<EventResponse> TopPopularityEvents(
//            String userId,
//            Double latitude,
//            Double longitude,
//            String location,
//            double radius);
//
//    List<EventResponse> weekEndEvents(
//            String userId,
//            Double latitude,
//            Double longitude,
//            String location,
//            double radius);

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
    
    CategoryPageResponse getCategoryEvents(
            String categoryName,
            String subCategoryName,
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius);
    
    public DiningEventResponse getDiningEvent(String eventId);
    

    
   
}