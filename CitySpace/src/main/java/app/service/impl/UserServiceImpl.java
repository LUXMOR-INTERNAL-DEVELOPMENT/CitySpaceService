package app.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import app.dao.impl.UserDaoImpl;
import app.dto.CategoryItemResponse;
import app.dto.CategoryPageResponse;
import app.dto.CategoryResponse;
import app.dto.DiningEventResponse;
import app.dto.EvenFilterRequest;
import app.dto.EventFilterResponse;
import app.dto.EventResponse;
import app.dto.HomeResponse;
import app.dto.ReviewRequest;
import app.dto.StatusUpdate;
import app.dto.UpcomingEventResponse;
import app.dto.UserResponse;
import app.dto.UserUpdateProfileRequest;
import app.entity.Category;
import app.entity.Event;
import app.entity.Offer;
import app.entity.RestaurantDetails;
import app.entity.Review;
import app.entity.User;
import app.exception.AccessDeniedException;
import app.exception.EventNotAvailableException;
import app.exception.UserIdNotFoundException;
import app.mapper.DiningEventResponseMapper;
import app.mapper.EventFilterMapper;
import app.mapper.EventResponseFilterMapper;
import app.mapper.EventResponseMapper;
import app.mapper.UpcomingEventMapper;
import app.mapper.UserResponseMapper;
import app.service.UserService;
import app.util.RatingCalculation;
import app.util.RatingUtil;
import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private UserResponseMapper mapper;

    @Autowired
    private EventResponseMapper eventResponseMapper;

    @Autowired
    private EventResponseFilterMapper eventFilterResponse;

    @Autowired
    private EventFilterMapper eventFilterMapper;

    @Autowired
    private DiningEventResponseMapper diningEventResponseMapper;

    @Autowired
    private UpcomingEventMapper upcomingEventMapper;

    @Autowired
    private RatingUtil ratingUtil;

    @Autowired
    private RatingCalculation ratingCalculation;
    

    @Override
    public HomeResponse getHomeEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

        // Overall events
        List<Event> overallEventList =
                userDao.getAllEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        Map<String, Double> overallRatings =ratingCalculation.
                calculateEventRatings(overallEventList);

        List<EventResponse> overallEvents =
                overallEventList.isEmpty()
                        ? List.of()
                        : eventResponseMapper.eventResponse(
                                overallEventList,
                                overallRatings
                        );

        // Popular events
        List<Event> popularEventList =
                userDao.TopPopularityEvent(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        Map<String, Double> popularRatings =ratingCalculation.
                calculateEventRatings(popularEventList);

        List<EventResponse> popularEvents =
                popularEventList.isEmpty()
                        ? List.of()
                        : eventResponseMapper.eventResponse(
                                popularEventList,
                                popularRatings
                        );

        // Upcoming events
        List<Event> upcomingEventList =
                userDao.getUpcomingEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        Map<String, Double> upcomingRatings =ratingCalculation.
                calculateEventRatings(upcomingEventList);

        List<UpcomingEventResponse> upcomingEvents =
                upcomingEventList.isEmpty()
                        ? List.of()
                        : upcomingEventMapper
                                .eventResponseUpcomingEvents(
                                        upcomingEventList,
                                        upcomingRatings
                                );

        // Weekend events
        List<Event> weekendEventList =
                userDao.weekEndEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        Map<String, Double> weekendRatings =ratingCalculation.
                calculateEventRatings(weekendEventList);

        List<EventResponse> weekendEvents =
                weekendEventList.isEmpty()
                        ? List.of()
                        : eventResponseMapper.eventResponse(
                                weekendEventList,
                                weekendRatings
                        );
        
     // Recommended events
        
        		List<Event> recommendedEventList =
                userDao.getRecommendedEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        Map<String, Double> recommendedRatings =
                ratingCalculation.calculateEventRatings(
                        recommendedEventList
                );

        List<EventResponse> recommendedEvents =
                recommendedEventList.isEmpty()
                        ? List.of()
                        : eventResponseMapper.eventResponse(
                                recommendedEventList,
                                recommendedRatings
                        );
        return new HomeResponse(
                overallEvents,
                popularEvents,
                upcomingEvents,
                weekendEvents,
                recommendedEvents
        );
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

        if (!user_id.equals(user.getUserId())
                || "VENDOR".equals(role)) {

            throw new AccessDeniedException(
                    "You cannot have access to modify profile"
            );
        }

        return userDao.updateProfileById(
                user_id,
                role,
                userProfile
        );
    }

    @Override
    public String updateStatus(
            String user_id,
            String role,
            StatusUpdate status) {

        User user = userDao.getUser(user_id);

        if (!user_id.equals(user.getUserId())
                || "VENDOR".equals(role)) {

            throw new AccessDeniedException(
                    "You cannot have access to modify profile"
            );
        }

        return userDao.updateStatus(
                user_id,
                role,
                status
        );
    }

    @Override
    public List<EventFilterResponse> getFilteredEvents(
            EvenFilterRequest filter) {

        List<Event> events = userDao.getActiveEvents();

        Map<String, Double> averageRatings =ratingCalculation.
                calculateEventRatings(events);

        List<Event> filteredEvents =
                eventFilterMapper.filter(
                        events,
                        filter,
                        averageRatings
                );

        return eventFilterResponse.response(
                filteredEvents,
                averageRatings
        );
    }

    @Override
    public CategoryPageResponse getCategoryEvents(
            String categoryName,
            String subCategoryName,
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

        List<Event> events =
                userDao.getNearbyCategoryEvents(
                        categoryName,
                        subCategoryName,
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        Map<String, Double> averageRatings =ratingCalculation.
                calculateEventRatings(events);

        List<CategoryItemResponse> items =
                eventResponseMapper.categoryItemResponse(
                        events,
                        averageRatings
                );

        if (items.isEmpty()) {
            throw new EventNotAvailableException(
                    "No " + categoryName
                            + " events are scheduled or available "
                            + "in the surrounding area"
            );
        }

        // If a subcategory is selected, return only matching items.
        if (subCategoryName != null
                && !subCategoryName.isBlank()) {

            return new CategoryPageResponse(
                    null,
                    items
            );
        }

        // Fetch categories for the selected main category.
        List<Category> categoryList =
                userDao.getCategories(categoryName);

        List<CategoryResponse> categories =
                categoryList.stream()
                        .map(category -> new CategoryResponse(
                                category.getCategoryId(),
                                category.getCategoryName(),
                                category.getCategoryImage()
                        ))
                        .toList();

        return new CategoryPageResponse(
                categories,
                items
        );
    }

    @Override
    public DiningEventResponse getDiningEvent(String eventId) {

        Event diningEvent = userDao.getDiningEvent(eventId);

        if (diningEvent == null) {
            throw new EventNotAvailableException(
                    "Event not available: " + eventId
            );
        }

        // Increment the click count for this event
        userDao.incrementClickCount(eventId);

        List<Double> ratings = userDao.getRatingsByEventId(eventId);

        double averageRating =
                ratingCalculation.calculateEventRating(eventId);

        List<Offer> offers = userDao.getOffers(eventId);
        List<Review> reviews = userDao.getReviews(eventId);

        RestaurantDetails menu =
                userDao.getRestaurantDetails(eventId);

        return diningEventResponseMapper.eventDiningResponse(
                diningEvent,
                offers,
                reviews,
                menu,
                averageRating
        );
    }

}



	
   

