package app.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.dao.EmptyResultDataAccessException;

import app.dao.UserDao;
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
import app.entity.Booking;
import app.entity.Coupon;
import app.entity.Event;
import app.entity.StoreBooking;
import app.entity.StoreCategory;
import app.entity.StoreProduct;
import app.entity.User;
import app.exception.AccessDeniedException;
import app.exception.BadRequestException;
import app.exception.EventNotAvailableException;
import app.exception.InsufficientQuantityException;
import app.exception.ResourceNotFoundException;
import app.exception.UserIdNotFoundException;
import app.mapper.EventFilterMapper;
import app.mapper.EventResponseFilterMapper;
import app.mapper.EventResponseMapper;
import app.mapper.UserResponseMapper;
import app.service.UserService;
import app.util.DistanceCalculator;
import app.util.OfferCalculation;

@Service
public class UserServiceImpl implements UserService {

        @Autowired
        private UserDao userDao;
        @Autowired
        private UserResponseMapper mapper;

        @Autowired
        private EventResponseMapper eventRespnseMapper;

        @Autowired
        private EventResponseFilterMapper eventFilterResponse;

        @Autowired
        private EventFilterMapper eventFilterMapper;

        @Override
        public List<Event> getAllEvents(
                        String userId,
                        Double latitude,
                        Double longitude,
                        String location,
                        double radius) {

                List<Event> event = userDao.getAllEvents(
                                userId,
                                latitude,
                                longitude,
                                location,
                                radius);

                if (event.isEmpty()) {
                        throw new EventNotAvailableException(
                                        "Events are not scheduled or available in the surrounding area");
                }

                return event;
        }

        @Override
        public List<EventResponse> TopPopularityEvents(
                        String userId,
                        Double latitude,
                        Double longitude,
                        String location,
                        double radius) {

                List<Event> events = userDao.TopPopularityEvent(
                                userId,
                                latitude,
                                longitude,
                                location,
                                radius);

                List<EventResponse> response = eventRespnseMapper.eventResponse(events);

                if (response.isEmpty()) {
                        throw new EventNotAvailableException(
                                        "Events are not scheduled or available in the surrounding area");
                }

                return response;
        }

        @Override
        public List<EventResponse> weekEndEvents(
                        String userId,
                        Double latitude,
                        Double longitude,
                        String location,
                        double radius) {

                List<Event> events = userDao.weekEndEvents(
                                userId,
                                latitude,
                                longitude,
                                location,
                                radius);

                List<EventResponse> response = eventRespnseMapper.eventResponse(events);

                if (response.isEmpty()) {
                        throw new EventNotAvailableException(
                                        "Events are not scheduled or available in the surrounding area");
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
        public String updateProfileById(
                        String user_id,
                        String role,
                        UserUpdateProfileRequest userProfile) {

                User user = userDao.getUser(user_id);

                if (!user.getUserId().equals(user_id)
                                || role.equals("VENDOR")) {

                        throw new AccessDeniedException(
                                        "You cannot have access to modify profile ");
                }

                return userDao.updateProfileById(
                                user_id,
                                role,
                                userProfile);
        }

        @Override
        public String updateStatus(
                        String user_id,
                        String role,
                        StatusUpdate status) {

                User user = userDao.getUser(user_id);

                if (!user.getUserId().equals(user_id)
                                || role.equals("VENDOR")) {

                        throw new AccessDeniedException(
                                        "You cannot have access to modify profile ");
                }

                return userDao.updateStatus(
                                user_id,
                                role,
                                status);
        }

        @Override
        public List<EventFilterResponse> getFilteredEvents(
                        EvenFilterRequest filter) {

                List<Event> events = userDao.getActiveEvents();

                List<Event> filteredEvents = eventFilterMapper.filter(events, filter);

                List<EventFilterResponse> response = eventFilterResponse.response(filteredEvents);

                return response;
        }

        @Override
        public List<StoreCategoryResponse> getNearbyStoreCategories(
                        double latitude,
                        double longitude,
                        double radius) throws BadRequestException {
                if (latitude < -90 || latitude > 90) {
                        throw new BadRequestException(
                                        "Latitude must be between -90 and 90");
                }

                if (longitude < -180 || longitude > 180) {
                        throw new BadRequestException(
                                        "Longitude must be between -180 and 180");
                }

                if (radius <= 0) {
                        throw new BadRequestException(
                                        "Radius must be greater than 0");
                }

                List<Event> events = userDao.getActiveStoreEvents();

                List<Event> nearbyEvents = events.stream()
                                .filter(event -> event.getLatitude() != null)
                                .filter(event -> event.getLongitude() != null)
                                .filter(event -> DistanceCalculator.calculateDistance(
                                                latitude,
                                                longitude,
                                                event.getLatitude(),
                                                event.getLongitude()) <= radius)
                                .toList();
                List<String> categoryIds = nearbyEvents.stream()
                                .map(Event::getCategoryId)
                                .distinct()
                                .toList();
                List<StoreCategory> categories = categoryIds.stream()
                                .map(categoryId -> userDao.getCategoryById(categoryId))
                                .toList();

                List<StoreCategoryResponse> response = categories.stream()
                                .map(category -> {
                                        StoreCategoryResponse result = new StoreCategoryResponse();

                                        result.setCategoryId(category.getCategoryId());
                                        result.setCategoryName(category.getCategoryName());

                                        return result;
                                })
                                .toList();

                return response;

        }

        @Override
        public List<Event> getStoreByCategory(String categoryId) {

                List<Event> events = userDao.getStoreByCategory(categoryId);

                if (events.isEmpty()) {
                        throw new EventNotAvailableException(
                                        "No stores are available for this category");
                }

                return events;
        }

        @Override
        public List<StoreListResponse> getAllStores(String categoryName) {
                return userDao.getAllStores(categoryName);
        }

        @Override
        public List<StoreListResponse> getNearbyStores(
                        double latitude,
                        double longitude,
                        String location,
                        double radius) {

                // Validate latitude
                if (!Double.isFinite(latitude)
                                || latitude < -90
                                || latitude > 90) {

                        throw new BadRequestException(
                                        "Latitude must be between -90 and 90");
                }

                // Validate longitude
                if (!Double.isFinite(longitude)
                                || longitude < -180
                                || longitude > 180) {

                        throw new BadRequestException(
                                        "Longitude must be between -180 and 180");
                }

                // Validate radius
                if (!Double.isFinite(radius)
                                || radius <= 0
                                || radius > 100) {

                        throw new BadRequestException(
                                        "Radius must be greater than 0 and must not exceed 100 km");
                }

                // Validate location
                if (location == null || location.isBlank()) {

                        throw new BadRequestException(
                                        "Location must not be blank");
                }

                // Fetch candidate stores from the selected city.
                // Coordinates are available internally for distance calculation.
                List<NearbyStoreCandidate> candidates = userDao.getNearbyStores(location);

                // Calculate distances, filter stores within the radius,
                // and convert candidates to the existing response DTO.
                return candidates.stream()

                                // Ignore candidates without coordinates.
                                .filter(store -> store.getLatitude() != null)
                                .filter(store -> store.getLongitude() != null)

                                // Keep stores within the requested radius.
                                .filter(store -> DistanceCalculator.calculateDistance(
                                                latitude,
                                                longitude,
                                                store.getLatitude(),
                                                store.getLongitude()) <= radius)

                                // Return only the five fields in StoreListResponse.
                                .map(store -> new StoreListResponse(
                                                store.getStoreId(),
                                                store.getStoreName(),
                                                store.getStoreLogo(),
                                                store.getStoreLocation(),
                                                store.getStoreCategory(),
                                                store.getOffer()))

                                .toList();
        }

        // Fetch all eligible stores in the selected city without radius filtering.
        public List<NearbyStoreCandidate> getStoresByLocation(String location) {

                if (location == null || location.isBlank()) {
                        throw new IllegalArgumentException("Location must not be empty");
                }

                return userDao.getStoresByLocation(location.trim());
        }

        @Override
        public List<StoreListResponse> filterStoresByCategory(String storeCategory) {
                return userDao.filterStoresByCategory(storeCategory);
        }

        @Override

        public StoreDetailsResponse getStoresDetails(String storeId) {

                return userDao.getStoresDetails(storeId);
        }

        @Override
        public List<StoreProductResponse> getProducts() {

                List<StoreProduct> products = userDao.getProducts();

                return products.stream()
                                .map(product -> {

                                        StoreProductResponse response = new StoreProductResponse();

                                        response.setProductName(
                                                        product.getProductName());

                                        response.setProductDescription(
                                                        product.getProductDescription());

                                        response.setProductPrice(
                                                        product.getProductPrice());

                                        response.setAvailableQuantity(
                                                        product.getAvailableQuantity());

                                        response.setProductImage(
                                                        product.getProductImage());

                                        return response;
                                })
                                .toList();
        }

        @Override
        public StoreProductResponse getProductById(String productId) {

                StoreProduct product = userDao.getProductById(productId);

                StoreProductResponse response = new StoreProductResponse();

                response.setProductName(product.getProductName());
                response.setProductDescription(product.getProductDescription());
                response.setProductPrice(product.getProductPrice());
                response.setAvailableQuantity(product.getAvailableQuantity());
                response.setProductImage(product.getProductImage());

                return response;
        }

        @Override
        public void submitStoreReview(ReviewRequest request) {
                userDao.submitStoreReview(request);
        }

        @Override
        public List<ReviewResponse> getRecentReviews(String eventId) {
                return userDao.getRecentReviews(eventId);
        }

        // COMMAND:
        // Fetch all active and currently valid coupons
        // for the coupon list screen.
        @Override
        public List<CouponResponse> getCouponList() {

                // COMMAND:
                // Get active coupons through the existing UserDao.
                List<Coupon> coupons = userDao.getActiveCoupons();

                // COMMAND:
                // Convert each Coupon entity into the response object.
                return coupons.stream()
                                .map(coupon -> {

                                        // COMMAND:
                                        // Create the coupon response object.
                                        CouponResponse response = new CouponResponse();

                                        // COMMAND:
                                        // Set the coupon name/category.
                                        response.setName(
                                                        coupon.getName());

                                        // COMMAND:
                                        // Set the coupon description.
                                        response.setDescription(
                                                        coupon.getDescription());

                                        // COMMAND:
                                        // Set the coupon code.
                                        response.setCouponCode(
                                                        coupon.getCouponCode());

                                        // COMMAND:
                                        // Set the image file name.
                                        response.setImage(
                                                        coupon.getImage());

                                        // COMMAND:
                                        // Set the discount display text.
                                        response.setDiscountDisplay(
                                                        coupon.getDiscountDisplay());

                                        response.setCategoryId(
                                                        coupon.getCategoryId());

                                        return response;
                                })
                                .toList();
        }

        // COMMAND:
        // Validate the booking and coupon,
        // then calculate the final booking amount.
        @Override
        public ApplyCouponResponse applyCoupon(
                        ApplyCouponRequest request) {

                // COMMAND:
                // Fetch the booking using its unique booking ID.
                Booking booking;

                try {
                        booking = userDao.getBookingById(
                                        request.getBookingId());

                } catch (EmptyResultDataAccessException e) {

                        // COMMAND:
                        // Stop when the booking does not exist.
                        throw new RuntimeException(
                                        "Booking not found");
                }

                // COMMAND:
                // Coupon can only be applied to a pending booking.
                if (!"PENDING".equalsIgnoreCase(
                                booking.getBookingStatus())) {

                        throw new RuntimeException(
                                        "Coupon can be applied only to pending booking");
                }

                // COMMAND:
                // Fetch the selected coupon using the coupon ID.
                Coupon coupon;

                try {
                        coupon = userDao.getCouponById(
                                        request.getCouponId());

                } catch (EmptyResultDataAccessException e) {

                        // COMMAND:
                        // Stop when the selected coupon does not exist.
                        throw new RuntimeException(
                                        "Coupon not found");
                }

                // COMMAND:
                // Make sure the coupon is currently ACTIVE.
                if (!"ACTIVE".equalsIgnoreCase(
                                coupon.getStatus())) {

                        throw new RuntimeException(
                                        "Coupon is not active");
                }

                // COMMAND:
                // Get today's date for coupon validity validation.
                LocalDate today = LocalDate.now();

                // COMMAND:
                // Reject the coupon when it has not started yet.
                if (today.isBefore(coupon.getValidFrom())) {

                        throw new RuntimeException(
                                        "Coupon is not yet valid");
                }

                // COMMAND:
                // Reject the coupon when it has expired.
                if (today.isAfter(coupon.getValidUntil())) {

                        throw new RuntimeException(
                                        "Coupon has expired");
                }

                // Weekday / Weekend validation
                LocalDateTime now = LocalDateTime.now();

                if ("WEEKEND".equalsIgnoreCase(coupon.getOfferType())) {

                        if (!OfferCalculation.isWeekend(now)) {
                                throw new RuntimeException(
                                                "This coupon is valid only on weekends");
                        }
                }

                if ("WEEKDAY".equalsIgnoreCase(coupon.getOfferType())) {

                        if (!OfferCalculation.isWeekday(now)) {
                                throw new RuntimeException(
                                                "This coupon is valid only on weekdays");
                        }
                }

                // COMMAND:
                // Get the original booking amount.
                BigDecimal bookingAmount = booking.getTotalAmount();

                // COMMAND:
                // Validate the minimum booking amount required
                // for the selected coupon.
                if (coupon.getMinimumBookingAmount() != null
                                && bookingAmount.compareTo(
                                                coupon.getMinimumBookingAmount()) < 0) {

                        throw new RuntimeException(
                                        "Minimum booking amount required is "
                                                        + coupon.getMinimumBookingAmount());
                }

                // COMMAND:
                // Calculate the offer amount using the offer type,
                // discount type, discount value and maximum discount.
                BigDecimal offerAmount = OfferCalculation.calculateDiscount(
                                bookingAmount,
                                coupon.getDiscountValue(),
                                coupon.getDiscountType(),
                                coupon.getOfferType(),
                                coupon.getMaximumDiscount());

                // COMMAND:
                // Calculate the final booking amount after applying
                // the coupon discount.
                BigDecimal finalAmount = OfferCalculation.calculateFinalAmount(
                                bookingAmount,
                                offerAmount);

                // COMMAND:
                // Create the coupon application response.
                ApplyCouponResponse response = new ApplyCouponResponse();

                // COMMAND:
                // Set the original booking amount.
                response.setTotalAmount(
                                bookingAmount);

                // COMMAND:
                // Set the calculated offer amount.
                response.setOfferAmount(
                                offerAmount);

                // COMMAND:
                // Set the final amount after applying the coupon.
                response.setFinalAmount(
                                finalAmount);

                // COMMAND:
                // Return the coupon calculation result.
                return response;
        }

        @Override
        public CouponApplicableDetailsResponse getApplicableCouponDetails(
                        String couponId) {

                // Get coupon details for discount calculation
                Coupon coupon;

                try {
                        coupon = userDao.getCouponById(couponId);

                } catch (EmptyResultDataAccessException e) {

                        throw new RuntimeException("Coupon not found");
                }

                // Check coupon status
                if (!"ACTIVE".equalsIgnoreCase(coupon.getStatus())) {

                        throw new RuntimeException("Coupon is not active");
                }

                // Check coupon validity dates
                LocalDate today = LocalDate.now();

                if (today.isBefore(coupon.getValidFrom())) {

                        throw new RuntimeException("Coupon is not yet valid");
                }

                if (today.isAfter(coupon.getValidUntil())) {

                        throw new RuntimeException("Coupon has expired");
                }

                // Check weekday / weekend offer
                LocalDateTime now = LocalDateTime.now();

                if ("WEEKEND".equalsIgnoreCase(coupon.getOfferType())) {

                        if (!OfferCalculation.isWeekend(now)) {

                                throw new RuntimeException(
                                                "This coupon is valid only on weekends");
                        }
                }

                if ("WEEKDAY".equalsIgnoreCase(coupon.getOfferType())) {

                        if (!OfferCalculation.isWeekday(now)) {

                                throw new RuntimeException(
                                                "This coupon is valid only on weekdays");
                        }
                }

                // Get category details
                CouponApplicableDetailsResponse response;

                try {

                        response = userDao.getApplicableCouponDetails(couponId);

                } catch (EmptyResultDataAccessException e) {

                        throw new RuntimeException(
                                        "Applicable category details not found");
                }

                // Original category price
                BigDecimal categoryPrice = response.getCategoryPrice();

                // Check minimum amount
                if (coupon.getMinimumBookingAmount() != null
                                && categoryPrice.compareTo(
                                                coupon.getMinimumBookingAmount()) < 0) {

                        throw new RuntimeException(
                                        "Minimum booking amount required is "
                                                        + coupon.getMinimumBookingAmount());
                }

                // Calculate discount
                BigDecimal offerAmount = OfferCalculation.calculateDiscount(
                                categoryPrice,
                                coupon.getDiscountValue(),
                                coupon.getDiscountType(),
                                coupon.getOfferType(),
                                coupon.getMaximumDiscount());

                // Calculate amount after discount
                BigDecimal totalAmount = OfferCalculation.calculateFinalAmount(
                                categoryPrice,
                                offerAmount);

                // Set calculated values in response
                response.setOfferAmount(offerAmount);
                response.setTotalAmount(totalAmount);

                return response;
        }

}
