package app.dao;

import java.util.List;


import org.springframework.stereotype.Repository;

import app.Entity.Event;
import app.Entity.User;
import app.dto.StatusUpdate;
import app.dto.UserUpdateProfileRequest;



@Repository
public interface UserDao {
	List<Event> getAllEvents(double latitude, double longitude, double radius);
	
	
	List<Event> TopPopularityEvent(double latitude, double longitude, double radius);
	
	
	List<Event> weekEndEvents(double latitude,double longitude,double radius);
	
	User getUser(String user_id );
	
	String updateProfileById(String user_id,String role,UserUpdateProfileRequest dto);
	
	String updateStatus(String user_id, String role,StatusUpdate status);
	 
	 
}
