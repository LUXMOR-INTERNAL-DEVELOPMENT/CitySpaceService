package api.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.Service.BookingService;
import api.entity.Booking;

@RestController
@RequestMapping("/api/bookings")
public class BookingControllerimpl {

    private final BookingService bookingService;

    public BookingControllerimpl(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable String userId) {
        return bookingService.getBookingsByUser(userId);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<Booking> getBookingsByVendor(@PathVariable String vendorId) {
        return bookingService.getBookingsByVendor(vendorId);
    }

    @GetMapping("/{bookingId}")
    public Booking getBookingById(@PathVariable String bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @GetMapping("/vendor/event/{eventId}")
    public List<Booking> getBookingsByEvent(@PathVariable String eventId) {
        return bookingService.getBookingsByEvent(eventId);
    }

    @GetMapping("/admin")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
}