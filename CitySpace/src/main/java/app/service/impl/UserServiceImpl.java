package app.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import app.dao.impl.UserDaoImpl;
import app.dto.AvailableTable;
import app.dto.BookingTableAllocation;
import app.dto.DiningBookingRequest;
import app.dto.DiningBookingResponse;
import app.dto.DiningEventResponse;
import app.dto.EvenFilterRequest;
import app.dto.EventFilterResponse;
import app.dto.EventResponse;
import app.dto.StatusUpdate;
import app.dto.UserResponse;
import app.dto.UserUpdateProfileRequest;
import app.entity.Booking;
import app.entity.Event;
import app.entity.Payment;
import app.entity.Restaurant;
import app.entity.TimeSlot;
import app.entity.User;
import app.exception.AccessDeniedException;
import app.exception.EventNotAvailableException;
import app.exception.InvalidBookingDateException;
import app.exception.InvalidGuestCountException;
import app.exception.SlotExpiredException;
import app.exception.SlotNotAvailableException;
import app.exception.TableNotAvailableException;
import app.exception.UserIdNotFoundException;
import app.exception.VendorNotAvailableException;
import app.mapper.DiningBookingResponseMapper;
import app.mapper.DiningEventResponseMapper;
import app.mapper.EventFilterMapper;
import app.mapper.EventResponseFilterMapper;
import app.mapper.EventResponseMapper;
import app.mapper.UserResponseMapper;
import app.service.UserService;
import app.util.DiningBookingValidator;
import app.util.GetNearByEvents;
import app.util.TableAllocationHandling;
import jakarta.transaction.Transactional;


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
    private EventFilterMapper  eventFilterMapper;
    
    @Autowired
    private DiningEventResponseMapper diningEventResponseMapper;
    
    @Autowired
    private DiningBookingResponseMapper diningBookingResponseMapper;
    
    @Autowired
    private DiningBookingValidator diningBookingValidator;
    
    @Autowired
    private TableAllocationHandling  tableAllocationHandling;
   

    @Override
    public List<Event> getAllEvents(
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

        List<Event> event =
                userDao.getAllEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        if (event.isEmpty()) {
            throw new EventNotAvailableException(
                    "Events are not scheduled or available in the surrounding area"
            );
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

        List<Event> events =
                userDao.TopPopularityEvent(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        List<EventResponse> response =
                eventResponseMapper.eventResponse(events);

        if (response.isEmpty()) {
            throw new EventNotAvailableException(
                    "Events are not scheduled or available in the surrounding area"
            );
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

        List<Event> events =
                userDao.weekEndEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        List<EventResponse> response =
                eventResponseMapper.eventResponse(events);

        if (response.isEmpty()) {
            throw new EventNotAvailableException(
                    "Events are not scheduled or available in the surrounding area"
            );
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

        if (!user_id.equals(user.getUserId())
                || "VENDOR".equals(role)) {

            throw new AccessDeniedException("You cannot have access to modify profile");
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

    	        throw new AccessDeniedException("You cannot have access to modify profile");
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


        List<Event> filteredEvents =
                eventFilterMapper.filter(events, filter);


        List<EventFilterResponse> response =eventFilterResponse.response(filteredEvents);


        return response;
    }
    
    @Override
    public List<EventResponse> getEventsByCategory(
            String categoryId,
            String userId,
            Double latitude,
            Double longitude,
            String location,
            double radius) {

        
    	 List<Event> events =
                 userDao.getNearbyEventsByCategory(categoryId, userId, latitude, longitude, location, radius);

         List<EventResponse> response =
                 eventResponseMapper.eventResponse(events);

         if (response.isEmpty()) {
             throw new EventNotAvailableException(
                     "Events are not scheduled or available in the surrounding area"
             );
         }

         return response;
       
    }

	
    
    @Override
	public DiningEventResponse getDiningEvent(String eventId) {
    	
		
		Event diningEvent=  userDao.getDiningEvent(eventId) ;
		return diningEventResponseMapper.eventDiningResponse(diningEvent);
		
	}

	
    @Override
    @Transactional
    public DiningBookingResponse bookingDining(
            String userId,
            String eventId,
            DiningBookingRequest request) {

        // 1. Validate guest count
        diningBookingValidator.validateGuestCount(
                request.getNumberOfGuests());

        // 2. Get user
        User user = userDao.getUser(userId);

        // 3. Get dining event
        Event event = userDao.getDiningEvent(eventId);

        // 4. Validate vendor
        diningBookingValidator.validateVendor(event);

        // 5. Get active slot
        TimeSlot slot =
                userDao.getActiveSlot(
                        request.getSlotId(),
                        event.getVendorId());

        // 6. Validate slot
        diningBookingValidator.validateSlot(slot);

        // 7. Validate booking date
        diningBookingValidator.validateBookingDate(
                request.getBookingDate(),
                slot);

        // 8. Calculate amount
        BigDecimal bookingAmount =
                request.getBaseAmount();

        BigDecimal gstPercentage =
                new BigDecimal("18");

        BigDecimal gstAmount =
                bookingAmount
                        .multiply(gstPercentage)
                        .divide(new BigDecimal("100"));

        BigDecimal convenienceFee =
                new BigDecimal("30");

        BigDecimal totalAmount =
                bookingAmount
                        .add(gstAmount)
                        .add(convenienceFee);

        // 9. Create booking
        String bookingId =
                "BOOK-" + UUID.randomUUID();

        Booking booking = new Booking();

        booking.setBookingId(bookingId);
        booking.setUserId(userId);
        booking.setId(eventId);
        booking.setVendorId(event.getVendorId());
        booking.setBookingDate(
                request.getBookingDate());
        booking.setSlotId(
                request.getSlotId());
        booking.setNumberOfGuests(
                request.getNumberOfGuests());
        booking.setTotalAmount(
                totalAmount);
        booking.setBookingStatus("PENDING");

        booking.setCreatedAt(LocalDateTime.now());
        booking.setCreatedBy(user.getRole());
        booking.setUpdatedAt(LocalDateTime.now());
        booking.setUpdatedBy(user.getRole());

        userDao.saveBooking(booking);

        // 10. Allocate tables
        tableAllocationHandling.allocateTables(
                event.getVendorId(),
                bookingId,
                request.getBookingDate(),
                request.getSlotId(),
                request.getNumberOfGuests());

        // 11. Create payment
        Payment payment = new Payment();

        payment.setPaymentId(
                "PAY-" + UUID.randomUUID());

        payment.setBookingId(bookingId);

        payment.setBookingAmount(
                bookingAmount);

        payment.setGstPercentage(
                gstPercentage);

        payment.setGstAmount(
                gstAmount);

        payment.setConvenienceFee(
                convenienceFee);

        payment.setTotalAmount(
                totalAmount);

        payment.setPaymentMethod(null);
        payment.setPaymentStatus("PENDING");
        payment.setTransactionId(null);
        payment.setPaymentDate(
                LocalDateTime.now());

        userDao.savePayment(payment);

        // 12. Response
        return diningBookingResponseMapper.map(
                booking,
                slot,
                payment,
                event);
    }
}

	
   

