package app.Service;

import java.util.List;

import app.entity.Booking;

public interface BookingService {

    List<Booking> getBookingsByUser(String userId);

    List<Booking> getBookingsByVendor(String vendorId);

    Booking getBookingById(String bookingId);

    List<Booking> getBookingsByEvent(String eventId);

    List<Booking> getAllBookings();
}