
package app.dao;

import java.util.List;

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


public interface UserDao {

    List<Event> getAllEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius);

    List<Event> TopPopularityEvent(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius);

    List<Event> weekEndEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius);

    User getUser(String user_id);
    
   

    String updateProfileById(
            String user_id,
            String role,
            UserUpdateProfileRequest dto);

    String updateStatus(
            String user_id,
            String role,
            StatusUpdate status);

    List<Event> getActiveEvents(); 
    
    List<Event> getNearbyEventsByCategory(
    		String categoryId,
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) ;
    
    Event getDiningEvent(String eventId);
    
    int saveBooking(Booking booking);

    int saveTableBooking(BookingTableAllocation bookingTable);

    int savePayment(Payment payment);

    TimeSlot getActiveSlot(
            String slotId,
            String vendorId);

    List<AvailableTable> getAvailableTables(
            String vendorId,
            String bookingDate,
            String slotId);
    
  
    public List<Restaurant> getTablesForUpdate(
            String vendorId) ;

  


}
