package app.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.dao.BookingDao;
import app.dto.BookingCartItem;
import app.dto.DirectBookingProduct;
import app.repository.BookingRepository;

@Repository
public class BookingDaoImpl implements BookingDao {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<BookingCartItem> getCartItemsForBooking(String userId) {
        return bookingRepository.getCartItemsForBooking(userId);
    }

    @Override
    public void createBooking(
            String bookingId,
            String userId,
            BigDecimal totalAmount) {

        bookingRepository.createBooking(
                bookingId,
                userId,
                totalAmount
        );
    }

    @Override
    public void createBookingItem(
            String bookingItemId,
            String bookingId,
            String productId,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal totalPrice,
            String userId) {

        bookingRepository.createBookingItem(
                bookingItemId,
                bookingId,
                productId,
                quantity,
                unitPrice,
                totalPrice,
                userId
        );
    }

    @Override
    public void clearCart(String userId) {
        bookingRepository.clearCart(userId);
    }
    
    @Override
    public void updateBookingStatus(String bookingId, String status) {
        bookingRepository.updateBookingStatus(bookingId, status);
    }
    
    @Override
    public DirectBookingProduct getProductForBooking(String productId) {
        return bookingRepository.getProductForBooking(productId);
    }
}