package app.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.dto.CouponApplicableDetailsResponse;
import app.entity.Booking;
import app.entity.Coupon;
import app.entity.Event;
import app.entity.StoreBooking;
import app.entity.StoreCategory;
import app.entity.StoreProduct;
import app.entity.User;
import app.mapper.CouponApplicableDetailsRowMapper;
import app.mapper.CouponRowMapper;
import app.mapper.EventRowMapper;
import app.mapper.StoreCategoryRowMapper;
import app.mapper.UserRowMapper;

import app.mapper.StoreProductRowMapper;

@Repository
public class UserRepository {
	
        @Autowired
	    private  JdbcTemplate jdbcTemplate;
        
        @Value("${event.getActiveEvents}")
	    private String getActiveEventsQuery;
        
        @Value("${store.getActiveStoreEvents}")
        private String getActiveStoreEventsQuery;
        
	    @Value("${user.getUserById}")
	    private String getUserByIdQuery;

	    @Value("${user.updateUser}")
	    private String updateUserQuery;
	    
	    @Value("${user.updateUserStatus}")
	    private String updateUserStatusQuery;
	    
	    @Value("${store.getCategoryById}")
	    private String getCategoryByIdQuery;
	    
	    @Value("${store.getStoreByCategory}")
	    private String getStoreByCategoryQuery;
	    
	 // COMMAND:
	    // Read the SQL query for fetching active and currently valid coupons.
	    @Value("${coupon.getActiveCoupons}")
	    private String getActiveCouponsQuery;

	    // COMMAND:
	    // Read the SQL query for fetching one coupon by coupon ID.
	    @Value("${coupon.getCouponById}")
	    private String getCouponByIdQuery;
	    
	 // COMMAND:
	 // Read the SQL query for fetching a booking by ID.
	 @Value("${coupon.getBookingById}")
	 private String getBookingByIdQuery;

	 @Value("${coupon.getApplicableDetails}")
	 private String getApplicableDetailsQuery; 
	  
	    
	    @Value("${store.getProductById}")
	    private String getProductByIdQuery;

	   
	    @Autowired
	    private UserRowMapper userRowMapper;

	    @Autowired
	    private EventRowMapper eventRowMapper;
	    
	    @Autowired
	    private StoreCategoryRowMapper storeCategoryRowMapper;
	    
	
	    
	    @Autowired
	    private StoreProductRowMapper storeProductRowMapper;
	    
	    @Autowired
	    private CouponRowMapper couponRowMapper;
	    
	    @Autowired
	    private CouponApplicableDetailsRowMapper couponApplicableDetailsRowMapper;


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
	   
	   public List<Event> getActiveStoreEvents() {

		    return jdbcTemplate.query(
		            getActiveStoreEventsQuery,
		            eventRowMapper
		    );
		}
	   
	   public StoreCategory getCategoryById(String categoryId) {

		    return jdbcTemplate.queryForObject(
		            getCategoryByIdQuery,
		            storeCategoryRowMapper,
		            categoryId
		    );
		}
	   
	   public List<Event> getStoreByCategory(String categoryId) {

		    return jdbcTemplate.query(
		            getStoreByCategoryQuery,
		            eventRowMapper,
		            categoryId
		    );
		}
	   
	
		
	   
	   
	   
	 public StoreProduct getProductById(String productId) {

		    return jdbcTemplate.queryForObject(
		        getProductByIdQuery,
		        storeProductRowMapper,
		        productId
		    );
		}
	 
	// COMMAND:
	 // Fetch all active and currently valid coupons from the database.
	 public List<Coupon> getActiveCoupons() {

	     return jdbcTemplate.query(
	             getActiveCouponsQuery,
	             couponRowMapper
	     );
	 }
	   
	
	//COMMAND:
	//Fetch one coupon using the coupon ID.
	public Coupon getCouponById(String couponId) {

	  return jdbcTemplate.queryForObject(
	          getCouponByIdQuery,
	          couponRowMapper,
	          couponId
	  );
	}

	//COMMAND:
	//Check whether the requested user exists and has ACTIVE status.
	/*public boolean isActiveUser(String userId) {

	 Integer count = jdbcTemplate.queryForObject(
	         checkActiveUserQuery,
	         Integer.class,
	         userId
	 );

	 // COMMAND:
	 // Return true only when an active user record exists.
	 return count != null && count > 0;
	}
	*/

	// COMMAND:
	// Fetch the booking using its unique booking ID.
	public Booking getBookingById(String bookingId) {

	    return jdbcTemplate.queryForObject(
	            getBookingByIdQuery,
	            (rs, rowNum) -> {

	                // COMMAND:
	                // Create the existing Booking entity.
	                Booking booking = new Booking();

	                // COMMAND:
	                // Map the booking ID.
	                booking.setBookingId(
	                        rs.getString("booking_id"));

	                // COMMAND:
	                // Map the user ID stored in the booking.
	                booking.setUserId(
	                        rs.getString("user_id"));

	                // COMMAND:
	                // Map the original booking amount.
	                booking.setTotalAmount(
	                        rs.getBigDecimal("total_amount"));

	                // COMMAND:
	                // Map the current booking status.
	                booking.setBookingStatus(
	                        rs.getString("booking_status"));

	                return booking;
	            },
	            bookingId
	    );
	}
	
	public CouponApplicableDetailsResponse getApplicableCouponDetails(
	        String couponId) {

	    return jdbcTemplate.queryForObject(
	            getApplicableDetailsQuery,
	            couponApplicableDetailsRowMapper,
	            couponId);
	}
	 
	}

