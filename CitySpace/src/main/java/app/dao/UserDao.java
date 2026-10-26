package app.dao;

import java.util.List;

import app.dto.AvailableTable;
import app.dto.BookingTableAllocation;
import app.dto.ReviewRequest;
import app.dto.StatusUpdate;
import app.dto.UserUpdateProfileRequest;
import app.entity.Booking;
import app.entity.Category;
import app.entity.Event;
import app.entity.RestaurantDetails;
import app.entity.Offer;
import app.entity.Payment;
import app.entity.RestaurantTable;
import app.entity.Review;
import app.entity.TimeSlot;
import app.entity.User;

public interface UserDao {

    List<Event> getAllEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius);

    List<Event> getUpcomingEvents(
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

    List<Event> getNearbyCategoryEvents(
            String categoryName,
            String subCategoryName,
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius);

    List<Category> getCategories(String categoryName);

    Event getDiningEvent(String eventId);

    // Dining detail data
    List<Offer> getOffers(String eventId);

    List<Review> getReviews(String eventId);

     RestaurantDetails getRestaurantDetails(String eventId);
     
     public List<Double> getRatingsByEventId(String eventId);
     
     void incrementClickCount(String eventId);
     
     List<Event> getRecommendedEvents(
     	    String userId,
     	    Double latitude,
     	    Double longitude,
     	    String location,
     	    double radius
     );
     
     
     
    
}
