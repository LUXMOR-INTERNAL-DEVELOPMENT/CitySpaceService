package app.dao.impl;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.Entity.Event;
import app.Entity.User;
import app.Exception.UserIdNotFoundException;
import app.dao.UserDao;
import app.dto.StatusUpdate;
import app.dto.UserUpdateProfileRequest;
import app.mapper.UserMapper;
import app.repository.EventRepository;
import app.repository.UserRepository;



@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired 
	private UserMapper userMapper;
	
	public List<Event> getAllEvents(double latitude, double longitude, double radius) {		
		return eventRepository.getAllByLocation(latitude, longitude,radius);
	}
	public List<Event> TopPopularityEvent(double latitude, double longitude, double radius) {
		
		return eventRepository.getPopularEventsByRating(latitude,longitude,radius);
	}

	

	public List<Event> weekEndEvents(double latitude,double longitude,double radius) {
		
		return eventRepository.getTopWeekendEventsByLocation(latitude,
                longitude,
                radius);
    }
	
	public User getUser(String id) {
		return userRepository.getUserById(id);
	}
	public String updateProfileById(String user_id,String role,UserUpdateProfileRequest dto) {
          User existingUser=userRepository.getUserById(user_id);
          if(existingUser==null) {
        	  throw new UserIdNotFoundException("user not found");
          }
         
          userMapper.updateUserFromDto(existingUser, dto);
          
          existingUser.setUpdatedAt(LocalDateTime.now());
    	  existingUser.setUpdatedBy(role);
    	  
    	  userRepository.updateUser(existingUser);
    	  
         
         return "Profile updated successfully";
         
         
	}
	public String updateStatus(String user_id,String role, StatusUpdate status) {
		 User existingUser=userRepository.getUserById(user_id);
		 if(existingUser==null) {
       	  throw new UserIdNotFoundException("user not found");
         }
         existingUser.setStatus(status.getStatus());
         
         existingUser.setUpdatedAt(LocalDateTime.now());
         
         existingUser.setUpdatedBy(role);
         
		 userRepository.updateUserStatus(existingUser); 
         
         return "status updated sucessfully";
	}

	
	
}
