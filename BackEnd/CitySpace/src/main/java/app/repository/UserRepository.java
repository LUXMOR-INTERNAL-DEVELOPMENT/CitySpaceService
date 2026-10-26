package app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.dto.CouponApplicableDetailsResponse;
import app.dto.NearbyStoreCandidate;
import app.dto.ReviewResponse;
import app.dto.StoreDetailsResponse;
import app.dto.StoreListResponse;
import app.entity.Booking;
import app.entity.Coupon;
import app.entity.Event;
import app.entity.Review;
import app.entity.StoreCategory;
import app.entity.StoreDetails;
import app.entity.StoreProduct;
import app.entity.User;
import app.mapper.CouponApplicableDetailsRowMapper;
import app.mapper.CouponRowMapper;
import app.mapper.EventRowMapper;
import app.mapper.NearbyStoreCandidateRowMapper;
import app.mapper.StoreCategoryRowMapper;
import app.mapper.StoreListRowMapper;
import app.mapper.StoreProductRowMapper;
import app.mapper.UserRowMapper;
import app.mapper.UserStoreDetailsRowMapper;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @Value("${store.getAllStores}")
    private String getAllStoresQuery;

    // Coupon queries
    @Value("${coupon.getActiveCoupons}")
    private String getActiveCouponsQuery;

    @Value("${coupon.getCouponById}")
    private String getCouponByIdQuery;

    @Value("${coupon.getBookingById}")
    private String getBookingByIdQuery;

    @Value("${coupon.getApplicableDetails}")
    private String getApplicableDetailsQuery;

    // Product queries
    @Value("${store.getProducts}")
    private String getProductsQuery;

    @Value("${store.getProductById}")
    private String getProductByIdQuery;

    // User-side Store Details query
    @Value("${store.getStoresDetails}")
    private String getStoresDetailsQuery;

    @Value("${store.filterByCategory}")
    private String filterStoresByCategoryQuery;

    // Nearby Stores query
    @Value("${store.getNearbyStores}")
    private String getNearbyStoresQuery;

    @Value("${store.getStoresByLocation}")
    private String getStoresByLocationQuery;

    @Value("${review.insert}")
    private String insertReviewQuery;

    @Value("${review.getRecentReviews}")
    private String getRecentReviewsQuery;

    // RowMappers
    @Autowired
    private UserRowMapper userRowMapper;

    @Autowired
    private EventRowMapper eventRowMapper;

    @Autowired
    private StoreCategoryRowMapper storeCategoryRowMapper;

    @Autowired
    private StoreListRowMapper storeListRowMapper;

    @Autowired
    private NearbyStoreCandidateRowMapper nearbyStoreCandidateRowMapper;

    @Autowired
    private UserStoreDetailsRowMapper userStoreDetailsRowMapper;

    @Autowired
    private StoreProductRowMapper storeProductRowMapper;

    @Autowired
    private CouponRowMapper couponRowMapper;

    @Autowired
    private CouponApplicableDetailsRowMapper couponApplicableDetailsRowMapper;

    // =========================
    // Event
    // =========================

    public List<Event> getActiveEvents() {

        return jdbcTemplate.query(
                getActiveEventsQuery,
                eventRowMapper);
    }

    // =========================
    // User
    // =========================

    public User getUserById(String user_id) {

        return jdbcTemplate.queryForObject(
                getUserByIdQuery,
                userRowMapper,
                user_id);
    }

    public int updateUser(User user) {

        return jdbcTemplate.update(
                updateUserQuery,
                user.getUserName(),
                user.getEmail(),
                user.getPhoneno(),
                user.getPassword(),
                user.getUserLocation(),
                user.getUpdatedBy(),
                user.getUserId());
    }

    public int updateUserStatus(User user) {

        return jdbcTemplate.update(
                updateUserStatusQuery,
                user.getStatus(),
                user.getUserId());
    }

    // =========================
    // Store Events
    // =========================

    public List<Event> getActiveStoreEvents() {

        return jdbcTemplate.query(
                getActiveStoreEventsQuery,
                eventRowMapper);
    }

    // =========================
    // Store Category
    // =========================

    public StoreCategory getCategoryById(String categoryId) {

        return jdbcTemplate.queryForObject(
                getCategoryByIdQuery,
                storeCategoryRowMapper,
                categoryId);
    }

    public List<Event> getStoreByCategory(String categoryId) {

        return jdbcTemplate.query(
                getStoreByCategoryQuery,
                eventRowMapper,
                categoryId);
    }

    // =========================
    // Nearby Stores
    // =========================

    /*
     * Fetch candidate stores in the selected city.
     * Latitude and longitude are retained internally
     * for distance calculations in UserServiceImpl.
     *
     * The API response will not expose these coordinates.
     */
    public List<NearbyStoreCandidate> getNearbyStores(String location) {

        return jdbcTemplate.query(
                getNearbyStoresQuery,
                nearbyStoreCandidateRowMapper,
                location);
    }

    // Fetch all eligible stores in the selected city without radius filtering.
    public List<NearbyStoreCandidate> getStoresByLocation(String location) {

        return jdbcTemplate.query(
                getStoresByLocationQuery,
                nearbyStoreCandidateRowMapper,
                location);
    }

    // =========================
    // Store Products
    // =========================

    public StoreProduct getProductById(String productId) {

        return jdbcTemplate.queryForObject(
                getProductByIdQuery,
                storeProductRowMapper,
                productId);
    }

    public List<StoreProduct> getProducts() {

        return jdbcTemplate.query(
                getProductsQuery,
                storeProductRowMapper);
    }

    // =========================
    // Store List
    // =========================

    public List<StoreListResponse> getAllStores(String categoryName) {
        return jdbcTemplate.query(
                getAllStoresQuery,
                storeListRowMapper,
                categoryName);
    }

    public List<StoreListResponse> filterStoresByCategory(String storeCategory) {

        return jdbcTemplate.query(
                filterStoresByCategoryQuery,
                storeListRowMapper,
                storeCategory);
    }

    public int insertReview(Review review) {

        return jdbcTemplate.update(
                insertReviewQuery,
                review.getReviewId(),
                review.getEventId(),
                review.getUserId(),
                review.getRating(),
                review.getReviewComment(),
                review.getCreatedBy());
    }

    public List<ReviewResponse> getRecentReviews(String eventId) {

        return jdbcTemplate.query(
                getRecentReviewsQuery,
                (rs, rowNum) -> new ReviewResponse(
                        rs.getObject("rating", Double.class),
                        rs.getString("review_comment"),
                        rs.getTimestamp("created_at") != null
                                ? rs.getTimestamp("created_at").toLocalDateTime()
                                : null),
                eventId);
    }

    // =========================
    // User Store Details
    // =========================

    public StoreDetailsResponse getStoresDetails(String storeId) {

        return jdbcTemplate.queryForObject(
                getStoresDetailsQuery,
                userStoreDetailsRowMapper,
                storeId);
    }

    // =========================
    // Coupons
    // =========================

    // Fetch all active and currently valid coupons
    public List<Coupon> getActiveCoupons() {

        return jdbcTemplate.query(
                getActiveCouponsQuery,
                couponRowMapper);
    }

    // Fetch one coupon using coupon ID
    public Coupon getCouponById(String couponId) {

        return jdbcTemplate.queryForObject(
                getCouponByIdQuery,
                couponRowMapper,
                couponId);
    }

    // =========================
    // Booking
    // =========================

    // Fetch booking using booking ID
    public Booking getBookingById(String bookingId) {

        return jdbcTemplate.queryForObject(
                getBookingByIdQuery,
                (rs, rowNum) -> {

                    Booking booking = new Booking();

                    booking.setBookingId(
                            rs.getString("booking_id"));

                    booking.setUserId(
                            rs.getString("user_id"));

                    booking.setTotalAmount(
                            rs.getBigDecimal("total_amount"));

                    booking.setBookingStatus(
                            rs.getString("booking_status"));

                    return booking;
                },
                bookingId);
    }

    // =========================
    // Applicable Coupon Details
    // =========================

    public CouponApplicableDetailsResponse getApplicableCouponDetails(
            String couponId) {

        return jdbcTemplate.queryForObject(
                getApplicableDetailsQuery,
                couponApplicableDetailsRowMapper,
                couponId);
    }

}