package app.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.Dao.BookingDao;
import app.entity.Booking;

@Service
public class BookingServiceimpl implements BookingService {

    private final BookingDao bookingDao;

    public BookingServiceimpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public List<Booking> getBookingsByUser(String userId) {
        return bookingDao.getBookingsByUser(userId);
    }

    @Override
    public List<Booking> getBookingsByVendor(String vendorId) {
        return bookingDao.getBookingsByVendor(vendorId);
    }

    @Override
    public Booking getBookingById(String bookingId) {
        return bookingDao.getBookingById(bookingId);
    }

    @Override
    public List<Booking> getBookingsByEvent(String eventId) {
        return bookingDao.getBookingsByEvent(eventId);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingDao.getAllBookings();
    }
}