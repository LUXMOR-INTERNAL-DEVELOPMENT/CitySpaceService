package app.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;



import app.Entity.Event;
import app.Entity.User;
import app.Exception.AccessDeniedException;
import app.Exception.EventNotAvailableException;
import app.Exception.UserIdNotFoundException;
import app.dao.impl.UserDaoImpl;
import app.dto.EventResponse;
import app.dto.StatusUpdate;
import app.dto.UserResponse;
import app.dto.UserUpdateProfileRequest;
import app.mapper.EventResponseMapper;
import app.mapper.UserResponseMapper;
import app.service.impl.UserService;



@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDaoImpl userDao;
	
	@Autowired
	private UserResponseMapper mapper;
	
	@Autowired
	private EventResponseMapper eventRespnseMapper;
	
	@Override
	public List<Event> getAllEvents(double latitude,double longitude,double radius) {
	
		
		  List<Event>event= userDao.getAllEvents( latitude, longitude,radius);
		  
		   if(event.isEmpty()) {
			  throw new EventNotAvailableException("Events are not scheduled or available in the surrounding area");
		  }
		  return event;
	}

	@Override
	public  List<EventResponse> TopPopularityEvents(double latitude,double longitude,double radius) {
		
		        List<Event>events= userDao.TopPopularityEvent(latitude, longitude, radius);
		        
		        List<EventResponse>response=eventRespnseMapper.eventResponse(events);
		              
		        if(response.isEmpty()) {
		        	throw new EventNotAvailableException("Events are not scheduled or available in the surrounding area");
		        }
		        return response;
	}
	
    @Override
	public List<EventResponse> weekEndEvents(double latitude,
            double longitude,
            double radius) {
		
		  List<Event>events= userDao.weekEndEvents(latitude,longitude,radius);
	      List<EventResponse>response=eventRespnseMapper.eventResponse(events);
	       if(response.isEmpty()) {
	    	   throw new EventNotAvailableException("Events are not scheduled or available in the surrounding area");
	       }
	       return response;
	  
	}
    
    
    @Override
	public UserResponse getUser(String user_id) {
		
		 try {

		        User user = userDao.getUser(user_id);

		        return mapper.userResponse(user);

		    } catch (EmptyResultDataAccessException e) {

		        throw new UserIdNotFoundException("User not found");
		    }
		
	}

    @Override
	public String updateProfileById(String user_id,String role, UserUpdateProfileRequest userProfile) {
    	User user=userDao.getUser(user_id);
    	if(!user.getUserId().equals(user_id) || role.equals("VENDOR")){
    		throw new AccessDeniedException("You cannot have access to modify profile ");
    	}
		
		return userDao.updateProfileById(user_id,role,userProfile);
	}

    @Override
	public String updateStatus(String user_id, String role, StatusUpdate status) {
    	User user=userDao.getUser(user_id);
    	if(!user.getUserId().equals(user_id) || role.equals("VENDOR")){
    		throw new AccessDeniedException("You cannot have access to modify profile ");
    	}
		return userDao.updateStatus(user_id,role,status);
	}

	
    
}
