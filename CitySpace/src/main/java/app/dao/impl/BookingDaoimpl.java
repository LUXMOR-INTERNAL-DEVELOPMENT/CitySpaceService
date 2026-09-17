package api.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import api.entity.Booking;

@Repository
public class BookingDaoimpl implements BookingDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${booking.getBookingsByUser}")
    private String getBookingsByUserQuery;

    @Value("${booking.getBookingsByVendor}")
    private String getBookingsByVendorQuery;

    @Value("${booking.getBookingById}")
    private String getBookingByIdQuery;

    @Value("${booking.getBookingsByEvent}")
    private String getBookingsByEventQuery;

    @Value("${booking.getAllBookings}")
    private String getAllBookingsQuery;

    public BookingDaoimpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Booking> rowMapper = new RowMapper<Booking>() {

        @Override
        public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {

            Booking booking = new Booking();

            booking.setBookingId(rs.getString("booking_id"));
            booking.setUserId(rs.getString("user_id"));
            booking.setVendorId(rs.getString("vendor_id"));
            booking.setEventId(rs.getString("event_id"));

            if (rs.getTimestamp("booking_date") != null) {
                booking.setBookingDate(
                    rs.getTimestamp("booking_date").toLocalDateTime()
                );
            }

            booking.setNumberOfTickets(rs.getInt("number_of_tickets"));
            booking.setAmount(rs.getDouble("amount"));
            booking.setStatus(rs.getString("status"));

            return booking;
        }
    };

    @Override
    public List<Booking> getBookingsByUser(String userId) {
        return jdbcTemplate.query(
            getBookingsByUserQuery,
            rowMapper,
            userId
        );
    }

    @Override
    public List<Booking> getBookingsByVendor(String vendorId) {
        return jdbcTemplate.query(
            getBookingsByVendorQuery,
            rowMapper,
            vendorId
        );
    }

    @Override
    public Booking getBookingById(String bookingId) {

        List<Booking> list = jdbcTemplate.query(
            getBookingByIdQuery,
            rowMapper,
            bookingId
        );

        if (list.isEmpty()) {
            throw new IllegalArgumentException("Booking not found");
        }

        return list.get(0);
    }

    @Override
    public List<Booking> getBookingsByEvent(String eventId) {
        return jdbcTemplate.query(
            getBookingsByEventQuery,
            rowMapper,
            eventId
        );
    }

    @Override
    public List<Booking> getAllBookings() {
        return jdbcTemplate.query(
            getAllBookingsQuery,
            rowMapper
        );
    }
}