package app.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import app.Entity.Event;
import app.dto.EventResponse;
import app.dto.StatusUpdate;
import app.dto.UserResponse;
import app.dto.UserUpdateProfileRequest;



@Service
public interface UserService {
	List<Event> getAllEvents(double latitude,double longitude,double radius);
	
    List<EventResponse> TopPopularityEvents(double latitude,double longitude,double radius);
    
    List<EventResponse> weekEndEvents(double latitude,
            double longitude,
            double radius) ;
    
    UserResponse getUser(String user_id);
    
    String updateProfileById(String user_id,String role,UserUpdateProfileRequest userProfile);
    
    String updateStatus(String user_id,String role, StatusUpdate status);
    
}
