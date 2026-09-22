package app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.Entity.Booking;
import app.Entity.Event;
import app.Entity.Payment;
import app.Entity.Restaurant;
import app.Entity.TimeSlot;
import app.Entity.User;
import app.dto.AvailableTable;
import app.dto.BookingTableAllocation;
import app.mapper.AvailableTableRowMapper;
import app.mapper.EventRowMapper;
import app.mapper.RestaurantRowMapper;
import app.mapper.TimeSlotRowMapper;
import app.mapper.UserRowMapper;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${event.getActiveEvents}")
    private String getActiveEventsQuery;

    @Value("${user.getUserById}")
    private String getUserByIdQuery;

    @Value("${user.updateUser}")
    private String updateUserQuery;

    @Value("${user.updateUserStatus}")
    private String updateUserStatusQuery;

    @Value("${event.getDiningevent}")
    private String getDiningEventDetailsQuery;

    @Value("${event.bookingDining}")
    private String bookingDiningQuery;

    @Value("${event.tableBooking}")
    private String tableBookingQuery;

    @Value("${event.payment}")
    private String paymentQuery;

    @Value("${event.getTimeSlot}")
    private String getTimeSlotQuery;

    @Value("${event.getAvailableTables}")
    private String getAvailableTablesQuery;

    @Value("${event.getTablesForUpdate}")
    private String getTablesForUpdateQuery;

    @Autowired
    private UserRowMapper userRowMapper;

    @Autowired
    private EventRowMapper eventRowMapper;

    @Autowired
    private TimeSlotRowMapper timeSlotRowMapper;

    @Autowired
    private AvailableTableRowMapper availableTableRowMapper;

    @Autowired
    private RestaurantRowMapper restaurantRowMapper;


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


    public int saveBooking(Booking booking) {

        return jdbcTemplate.update(
                bookingDiningQuery,

                booking.getBookingId(),
                booking.getUserId(),
                booking.getId(),
                booking.getVendorId(),
                booking.getBookingDate(),
                booking.getSlotId(),
                booking.getNumberOfGuests(),
                booking.getTotalAmount(),
                booking.getBookingStatus(),
                booking.getCreatedAt(),
                booking.getCreatedBy(),
                booking.getUpdatedAt(),
                booking.getUpdatedBy()
        );
    }


    public int saveBookingTableAllocation(
            BookingTableAllocation bookingTable) {

        return jdbcTemplate.update(
                tableBookingQuery,

                bookingTable.getBookingTableAllocationId(),
                bookingTable.getBookingId(),
                bookingTable.getTableId(),
                bookingTable.getSlotId(),
                bookingTable.getBookingDate(),
                bookingTable.getNumberOfGuests(),
                bookingTable.getBookingStatus()
        );
    }


    public int savePayment(Payment payment) {

        return jdbcTemplate.update(
                paymentQuery,
                payment.getPaymentId(),
                payment.getBookingId(),
                payment.getBookingAmount(),
                payment.getGstPercentage(),
                payment.getGstAmount(),
                payment.getConvenienceFee(),
                payment.getTotalAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getTransactionId(),
                payment.getPaymentDate()
        );
    }


    public List<AvailableTable> getAvailableTables(
            String vendorId,
            String bookingDate,
            String slotId) {

        return jdbcTemplate.query(
                getAvailableTablesQuery,
                availableTableRowMapper,
                vendorId,
                bookingDate,
                slotId
        );
    }


    public TimeSlot getActiveSlot(
            String slotId,
            String vendorId) {

        return jdbcTemplate.queryForObject(
                getTimeSlotQuery,
                timeSlotRowMapper,
                slotId,
                vendorId
        );
    }


    public List<Restaurant> getTablesForUpdate(
            String vendorId) {

        return jdbcTemplate.query(
                getTablesForUpdateQuery,
                restaurantRowMapper,
                vendorId
        );
    }
}