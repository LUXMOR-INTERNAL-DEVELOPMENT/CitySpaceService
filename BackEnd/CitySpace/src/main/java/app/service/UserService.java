package app.service;

import java.util.List;

import org.apache.coyote.BadRequestException;

import app.dto.ApplyCouponRequest;
import app.dto.ApplyCouponResponse;
import app.dto.CouponApplicableDetailsResponse;
import app.dto.CouponResponse;
import app.dto.EvenFilterRequest;
import app.dto.EventFilterResponse;
import app.dto.EventResponse;
import app.dto.NearbyStoreCandidate;
import app.dto.PaymentConfirmationRequest;
import app.dto.ReviewRequest;
import app.dto.ReviewResponse;
import app.dto.StatusUpdate;
import app.dto.StoreBookingRequest;
import app.dto.StoreBookingResponse;
import app.dto.StoreCategoryResponse;
import app.dto.StoreDetailsResponse;
import app.dto.StoreListResponse;
import app.dto.StoreProductResponse;
import app.dto.UserResponse;
import app.dto.UserUpdateProfileRequest;
import app.entity.Event;

public interface UserService {

        List<Event> getAllEvents(
                        String userId,
                        Double latitude,
                        Double longitude,
                        String location,
                        double radius);

        List<EventResponse> TopPopularityEvents(
                        String userId,
                        Double latitude,
                        Double longitude,
                        String location,
                        double radius);

        List<EventResponse> weekEndEvents(
                        String userId,
                        Double latitude,
                        Double longitude,
                        String location,
                        double radius);

        UserResponse getUser(String user_id);

        String updateProfileById(
                        String user_id,
                        String role,
                        UserUpdateProfileRequest userProfile);

        String updateStatus(
                        String user_id,
                        String role,
                        StatusUpdate status);

        List<EventFilterResponse> getFilteredEvents(EvenFilterRequest filter);

        List<StoreCategoryResponse> getNearbyStoreCategories(
                        double latitude,
                        double longitude,
                        double radius) throws BadRequestException;

        List<Event> getStoreByCategory(String categoryId);

        List<StoreListResponse> getAllStores(String categoryName);

        List<StoreListResponse> getNearbyStores(
                        double latitude,
                        double longitude,
                        String location,
                        double radius);

        List<NearbyStoreCandidate> getStoresByLocation(String location);

        List<StoreListResponse> filterStoresByCategory(String storeCategory);

        StoreDetailsResponse getStoresDetails(String storeId);

        List<StoreProductResponse> getProducts();

        StoreProductResponse getProductById(String productId);

        void submitStoreReview(ReviewRequest request);

        List<ReviewResponse> getRecentReviews(String eventId);

        // COMMAND:
        // Fetch all active and currently valid coupons for the coupon list screen.
        List<CouponResponse> getCouponList();

        // COMMAND:
        // Validate the user, booking and coupon, then calculate the final amount.
        ApplyCouponResponse applyCoupon(ApplyCouponRequest request);

        CouponApplicableDetailsResponse getApplicableCouponDetails(String couponId);

}
