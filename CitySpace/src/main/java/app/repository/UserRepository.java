package app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.Entity.Event;
import app.Entity.User;
import app.mapper.EventRowMapper;
import app.mapper.UserRowMapper;

@Repository
public class UserRepository {
	
        @Autowired
	    private  JdbcTemplate jdbcTemplate;
        
        @Value("${event.getActiveEvents}")
	    private String getActiveEventsQuery;
        
        
	    @Value("${user.getUserById}")
	    private String getUserByIdQuery;

	    @Value("${user.updateUser}")
	    private String updateUserQuery;
	    
	    @Value("${user.updateUserStatus}")
	    private String updateUserStatusQuery;

	   
	    @Autowired
	    private UserRowMapper userRowMapper;
	    
	    

	    @Autowired
	    private EventRowMapper eventRowMapper;


	    public List<Event> getActiveEvents() {

	        return jdbcTemplate.query(
	                getActiveEventsQuery,
	                eventRowMapper
	        );
	    }
	    
	   
	    public User getUserById(String user_id) {

	        return jdbcTemplate.queryForObject(
	                getUserByIdQuery,
	                userRowMapper,
	                user_id
	        );
	    }

	    public int updateUser(User user) {

	        return jdbcTemplate.update(updateUserQuery,
	                user.getUserName(),       
	                user.getEmail(),          
	                user.getPhoneno(),        
	                user.getPassword(),      
	                user.getUserLocation(),   
	                user.getUpdatedBy(),      
	                user.getUserId()         
	        );
	    }

	   public int updateUserStatus(User user) {

		    return jdbcTemplate.update(
		        updateUserStatusQuery,
		        user.getStatus(),
		        user.getUserId()
		    );
		}
	   
	   
	}

