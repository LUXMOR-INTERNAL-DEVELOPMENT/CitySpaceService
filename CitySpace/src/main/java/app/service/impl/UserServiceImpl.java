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

import app.Entity.Booking;

import app.Entity.Event;
import app.Entity.Payment;
import app.Entity.Restaurant;
import app.Entity.TimeSlot;
import app.Entity.User;

import app.Exception.AccessDeniedException;

import app.Exception.EventNotAvailableException;
import app.Exception.InvalidBookingDateException;
import app.Exception.InvalidGuestCountException;
import app.Exception.SlotExpiredException;
import app.Exception.SlotNotAvailableException;
import app.Exception.TableNotAvailableException;
import app.Exception.UserIdNotFoundException;
import app.Exception.VendorNotAvailableException;
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
import app.mapper.DiningBookingResponseMapper;
import app.mapper.DiningEventResponseMapper;
import app.mapper.EventFilterMapper;
import app.mapper.EventResponseFilterMapper;
import app.mapper.EventResponseMapper;
import app.mapper.UserResponseMapper;
import app.service.UserService;
import app.util.GetNearByEvents;
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

        // 1. Validate number of guests
        if (request.getNumberOfGuests() <= 0) {
            throw new InvalidGuestCountException(
                    "Number of guests must be greater than 0");
        }

        int numberOfGuests = request.getNumberOfGuests();

        //Get user
        User user = userDao.getUser(userId);

        //Get dining event
        Event event = userDao.getDiningEvent(eventId);

        //Validate vendor
        if (event == null || event.getVendorId() == null) {
            throw new VendorNotAvailableException(
                    "Vendor is not available for this dining event");
        }

        // 5. Get active slot
        TimeSlot slot = userDao.getActiveSlot(
                request.getSlotId(),
                event.getVendorId());

        if (slot == null) {
            throw new SlotNotAvailableException(
                    "Selected time slot is not available");
        }

        // 6. Validate booking date
        LocalDate bookingDate =
                LocalDate.parse(request.getBookingDate());

        LocalDate today =
                LocalDate.now();

        if (bookingDate.isBefore(today)) {
            throw new InvalidBookingDateException(
                    "Booking date cannot be in the past");
        }

        // 7. If booking is today, check slot expiry
        if (bookingDate.equals(today)) {

            LocalTime currentTime =
                    LocalTime.now();

            LocalTime slotEndTime =
                    LocalTime.parse(slot.getSlotEndTime());

            if (!currentTime.isBefore(slotEndTime)) {
                throw new SlotExpiredException(
                        "Selected time slot has already ended");
            }
        }

        // 8. LOCK RESTAURANT TABLES
        //
        // This prevents two users from allocating
        // the same tables at the same time.
        List<Restaurant> tables =
                userDao.getTablesForUpdate(
                        event.getVendorId());

        if (tables == null || tables.isEmpty()) {
            throw new TableNotAvailableException(
                    "No tables are available for this vendor");
        }

        // 9. GET TABLES AVAILABLE FOR THIS
        //    DATE + SLOT
        //
        // A table is completely unavailable if another
        // booking already has that table for this
        // date and slot.

        List<AvailableTable> availableTables =
                userDao.getAvailableTables(
                        event.getVendorId(),
                        request.getBookingDate(),
                        request.getSlotId());

        if (availableTables == null ||
                availableTables.isEmpty()) {

            throw new TableNotAvailableException(
                    "No tables are available for the selected "
                    + "date and time slot");
        }

        // 10. Calculate total capacity of available
        //     WHOLE tables
        int totalCapacity = 0;

        for (AvailableTable table : availableTables) {

            totalCapacity += table.getCapacity();
        }

        // 11. Check whether enough WHOLE table capacity
        //     is available
        if (totalCapacity < numberOfGuests) {

            throw new TableNotAvailableException(
                    "Not enough table capacity available "
                    + "for the selected date and time slot");
        }

        // 12. CALCULATE AMOUNT

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

        // 13. CREATE BOOKING

        String bookingId =
                "BOOK-" + UUID.randomUUID();

        Booking booking =
                new Booking();

        booking.setBookingId(bookingId);

        booking.setUserId(userId);

       
        booking.setId(eventId);

        booking.setVendorId(
                event.getVendorId());

        booking.setBookingDate(
                request.getBookingDate());

        booking.setSlotId(
                request.getSlotId());

        booking.setNumberOfGuests(
                numberOfGuests);

        booking.setTotalAmount(
                totalAmount);

        // Payment not completed yet
        booking.setBookingStatus("PENDING");

        booking.setCreatedAt(
                LocalDateTime.now());

        booking.setCreatedBy(
                user.getRole());

        booking.setUpdatedAt(
                LocalDateTime.now());

        booking.setUpdatedBy(
                user.getRole());

        userDao.saveBooking(booking);

        //AUTOMATICALLY ALLOCATE WHOLE TABLES

        int remainingGuests =
                numberOfGuests;

        for (AvailableTable table : availableTables) {

            if (remainingGuests <= 0) {
                break;
            }

            // Allocate the entire table.
            // The table cannot be shared with
            // another booking.

            int allocatedGuests =
                    Math.min(
                            remainingGuests,
                            table.getCapacity());

            BookingTableAllocation bookingTable =
                    new BookingTableAllocation();

            bookingTable.setBookingTableAllocationId(
                    "BT-" + UUID.randomUUID());

            bookingTable.setBookingId(
                    bookingId);

            bookingTable.setTableId(
                    table.getTableId());

            bookingTable.setSlotId(
                    request.getSlotId());

            bookingTable.setBookingDate(
                    request.getBookingDate());

            bookingTable.setNumberOfGuests(
                    allocatedGuests);

            // Payment is still pending
            bookingTable.setBookingStatus(
                    "RESERVED");

            userDao.saveTableBooking(
                    bookingTable);

            remainingGuests -=
                    allocatedGuests;
        }

        //SAFETY CHECK

        if (remainingGuests > 0) {

            throw new TableNotAvailableException(
                    "Unable to allocate tables for "
                    + "all requested guests");
        }

        //CREATE PAYMENT RECORD

        Payment payment =
                new Payment();

        payment.setPaymentId(
                "PAY-" + UUID.randomUUID());

        payment.setBookingId(
                booking.getBookingId());

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

        // Actual payment gateway will update
        // these values later.
        payment.setPaymentMethod(null);

        payment.setPaymentStatus(
                "PENDING");

        payment.setTransactionId(null);

        payment.setPaymentDate(
                LocalDateTime.now());

        userDao.savePayment(payment);

        //SUCCESS RESPONSE

        return diningBookingResponseMapper.map(
                booking,
                slot,
                payment,
                event
        );
    }

}
