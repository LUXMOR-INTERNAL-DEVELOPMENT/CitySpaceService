package app.dao.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.dao.UserDao;
import app.dto.AvailableTable;
import app.dto.BookingTableAllocation;
import app.dto.StatusUpdate;
import app.dto.UserUpdateProfileRequest;
import app.entity.Booking;
import app.entity.Event;
import app.entity.Payment;
import app.entity.Restaurant;
import app.entity.TimeSlot;
import app.entity.User;
import app.exception.UserIdNotFoundException;
import app.mapper.UserMapper;
import app.repository.UserRepository;
import app.util.GetNearByEvents;
import app.util.GetPopularityEvents;
import app.util.WeekEndEvents;


@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private  GetNearByEvents getNearEvents;

    @Autowired
    private GetPopularityEvents getPopularityEvents;

    @Autowired
    private WeekEndEvents weekEndEvents;


    private List<Event> getNearbyEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

    	 return getNearEvents.getNearbyEvents(
    	            userId,
    	            latitude,
    	            longitude,
    	            location,
    	            radius
    	    );
    }
   


    @Override
    public List<Event> getAllEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

        return getNearbyEvents(
                userId,
                latitude,
                longitude,
                location,
                radius
        );
    }


    @Override
    public List<Event> TopPopularityEvent(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

    	 return getPopularityEvents.getPopularityEvents(
    	            userId,
    	            latitude,
    	            longitude,
    	            location,
    	            radius
    	    );
    }


    @Override
    public List<Event> weekEndEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

    	return weekEndEvents.getWeekendEvents(
                userId,
                latitude,
                longitude,
                location,
                radius
        );
    }


    @Override
    public User getUser(String id) {

        return userRepository.getUserById(id);
    }


    @Override
    public String updateProfileById(
            String user_id,
            String role,
            UserUpdateProfileRequest dto) {

        User existingUser =
                userRepository.getUserById(user_id);

        if (existingUser == null) {
            throw new UserIdNotFoundException(
                    "user not found"
            );
        }

        userMapper.updateUserFromDto(
                existingUser,
                dto
        );

        existingUser.setUpdatedAt(
                LocalDateTime.now()
        );

        existingUser.setUpdatedBy(role);

        userRepository.updateUserProfile(
                existingUser
        );

        return "Profile updated successfully";
    }


    @Override
    public String updateStatus(
            String user_id,
            String role,
            StatusUpdate status) {

        User existingUser =
                userRepository.getUserById(user_id);

        if (existingUser == null) {
            throw new UserIdNotFoundException(
                    "user not found"
            );
        }
        

        userRepository.updateUserStatus(
                existingUser
        );

        existingUser.setStatus(
                status.getStatus()
        );

        existingUser.setUpdatedAt(
                LocalDateTime.now()
        );

        existingUser.setUpdatedBy(role);


        return "status updated successfully";
    }

    @Override
    public List<Event> getActiveEvents() {
        return userRepository.getActiveEvents();
    }

    @Override
    public List<Event> getNearbyEventsByCategory(
    		String categoryId,
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

    	 return getNearEvents.getNearbyEventsByCategory(
    			    categoryId,
    	            userId,
    	            latitude,
    	            longitude,
    	            location,
    	            radius
    	    );
    }
	

    @Override
	public Event getDiningEvent(String eventId) {
		
		return userRepository.getDiningEvent(eventId);
	}

   
    @Override
    public int saveBooking(Booking booking) {

        return userRepository.saveBooking(booking);
    }
    @Override
    public int saveTableBooking(
            BookingTableAllocation bookingTable) {

        return userRepository.saveBookingTableAllocation(
                bookingTable);
    }
    @Override
    public int savePayment(Payment payment) {

        return userRepository.savePayment(payment);
    }
    @Override
    public TimeSlot getActiveSlot(
            String slotId,
            String vendorId) {

        return userRepository.getActiveSlot(
                slotId,
                vendorId);
    }



    @Override
    public List<AvailableTable> getAvailableTables(
            String vendorId,
            String bookingDate,
            String slotId) {

        return userRepository.getAvailableTables(
                vendorId,
                bookingDate,
                slotId);
    }
    
    
    @Override
    public List<Restaurant> getTablesForUpdate(
            String vendorId) {

        return userRepository.getTablesForUpdate(vendorId);
    }

   


	


}
