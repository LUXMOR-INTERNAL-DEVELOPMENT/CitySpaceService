package app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.dto.ReviewRequest;
import app.entity.Category;
import app.entity.Event;

import app.entity.Offer;
import app.entity.RestaurantDetails;
import app.entity.Review;
import app.entity.User;
import app.mapper.CategoryRowMapper;
import app.mapper.EventRowMapper;

import app.mapper.OfferRowMapper;
import app.mapper.RestaurantDetailsRowMapper;

import app.mapper.ReviewRowMapper;
import app.mapper.UserRowMapper;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${event.getActiveEvents}")
    private String getActiveEventsQuery;
    
    @Value("${event.getRatings}")
    private String getRatingsQuery;

    @Value("${user.getUserById}")
    private String getUserByIdQuery;

    @Value("${user.updateUser}")
    private String updateUserQuery;

    @Value("${user.updateUserStatus}")
    private String updateUserStatusQuery;

    @Value("${event.getDiningevent}")
    private String getDiningEventDetailsQuery;

    @Value("${category.getCategories}")
    private String getCategoriesQuery;

    @Value("${event.getOffers}")
    private String getOffersQuery;

    @Value("${event.getReviews}")
    private String getReviewsQuery;

    @Value("${event.getRestaurantDetails}")
    private String getRestaurantDetailsQuery;
   
    
    @Value("${event.incrementClickCount}")
    private String incrementClickCountQuery;
    
    
    
   


    @Autowired
    private CategoryRowMapper categoryRowMapper;

    @Autowired
    private UserRowMapper userRowMapper;

    @Autowired
    private EventRowMapper eventRowMapper;

    @Autowired
    private OfferRowMapper offerRowMapper;

    @Autowired
    private ReviewRowMapper restaurantReviewRowMapper;

    @Autowired
    private RestaurantDetailsRowMapper restaurantDetailsRowMapper;


    public List<Event> getActiveEvents() {

        return jdbcTemplate.query(
                getActiveEventsQuery,
                eventRowMapper
        );
    }


    public List<Category> getCategories(String categoryName) {

        return jdbcTemplate.query(
                getCategoriesQuery,
                categoryRowMapper,
                categoryName
        );
    }


    public User getUserById(String user_id) {

        return jdbcTemplate.queryForObject(
                getUserByIdQuery,
                userRowMapper,
                user_id
        );
    }


    public int updateUserProfile(User user) {

        return jdbcTemplate.update(
                updateUserQuery,
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


    public Event getDiningEvent(String eventId) {

        return jdbcTemplate.queryForObject(
                getDiningEventDetailsQuery,
                eventRowMapper,
                eventId
        );
    }


    // Get active offers for the event
    public List<Offer> getOffers(String eventId) {

        return jdbcTemplate.query(
                getOffersQuery,
                offerRowMapper,
                eventId
        );
    }


    // Get reviews for the event
    public List<Review> getReviews(String eventId) {

        return jdbcTemplate.query(
                getReviewsQuery,
                restaurantReviewRowMapper,
                eventId
        );
    }
    
    

    
 
 // Get restaurant details for the event
    public RestaurantDetails getRestaurantDetails(String eventId) {

        List<RestaurantDetails> restaurantDetails = jdbcTemplate.query(
                getRestaurantDetailsQuery,
                restaurantDetailsRowMapper,
                eventId
        );

        if (restaurantDetails.isEmpty()) {
            return null;
        }

        return restaurantDetails.get(0);
    }

    
    public void incrementClickCount(String eventId) {
        jdbcTemplate.update(incrementClickCountQuery, eventId);
    }
    

   
}