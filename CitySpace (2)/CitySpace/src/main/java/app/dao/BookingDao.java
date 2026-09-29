package app.dao;

import java.math.BigDecimal;
import java.util.List;

import app.dto.BookingCartItem;
import app.dto.DirectBookingProduct;

public interface BookingDao {

    List<BookingCartItem> getCartItemsForBooking(String userId);

    void createBooking(
            String bookingId,
            String userId,
            BigDecimal totalAmount);

    void createBookingItem(
            String bookingItemId,
            String bookingId,
            String productId,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal totalPrice,
            String userId);

    void clearCart(String userId);
    
    void updateBookingStatus(String bookingId, String status);
    
    DirectBookingProduct getProductForBooking(String productId);
}