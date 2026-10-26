package app.dao;

import java.math.BigDecimal;
import java.util.List;

import app.dto.CouponApplicableDetailsResponse;
import app.dto.EvenFilterRequest;
import app.dto.NearbyStoreCandidate;
import app.dto.ReviewRequest;
import app.dto.ReviewResponse;
import app.dto.StatusUpdate;
import app.dto.StoreDetailsResponse;
import app.dto.StoreListResponse;
import app.dto.UserUpdateProfileRequest;
import app.entity.Booking;
import app.entity.Coupon;
import app.entity.Event;
import app.entity.StoreBooking;
import app.entity.StoreCategory;
import app.entity.StoreProduct;
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

        List<Event> getActiveStoreEvents();

        StoreCategory getCategoryById(String categoryId);

        List<Event> getStoreByCategory(String categoryId);

        List<StoreListResponse> getAllStores(String categoryName);

        List<NearbyStoreCandidate> getNearbyStores(String location);

        List<NearbyStoreCandidate> getStoresByLocation(String location);

        List<StoreListResponse> filterStoresByCategory(String storeCategory);

        StoreDetailsResponse getStoresDetails(String storeId);

        List<StoreProduct> getProducts();

        StoreProduct getProductById(String productId);

        void submitStoreReview(ReviewRequest request);

        List<ReviewResponse> getRecentReviews(String eventId);

        // COMMAND:
        // Fetch all active and currently valid coupons.
        List<Coupon> getActiveCoupons();

        // COMMAND:
        // Fetch one coupon using the coupon ID.
        Coupon getCouponById(String couponId);

        // COMMAND:
        // Fetch a booking using its unique booking ID.
        // The booking provides the original amount for coupon calculation.
        Booking getBookingById(String bookingId);

        CouponApplicableDetailsResponse getApplicableCouponDetails(String couponId);

}
