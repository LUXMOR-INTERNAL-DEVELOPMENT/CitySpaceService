package app.Repository;

import java.util.List;

import api.entity.Booking;

public interface BookingRepository {

    List<Booking> getBookingsByUser(String userId);

    List<Booking> getBookingsByVendor(String vendorId);

    Booking getBookingById(String bookingId);

    List<Booking> getBookingsByEvent(String eventId);

    List<Booking> getAllBookings();
}