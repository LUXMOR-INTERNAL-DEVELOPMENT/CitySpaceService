package app.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.dto.BookingCartItem;
import app.dto.DirectBookingProduct;

@Repository
public class BookingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${store.getCartItemsForBooking}")
    private String getCartItemsForBooking;

    @Value("${store.createBooking}")
    private String createBooking;

    @Value("${store.createBookingItem}")
    private String createBookingItem;

    @Value("${store.clearCart}")
    private String clearCart;
    
    @Value("${store.updateBookingStatus}")
    private String updateBookingStatus;
    
    @Value("${store.getProductForBooking}")
    private String getProductForBooking;

    // Get all cart products for the user
    public List<BookingCartItem> getCartItemsForBooking(String userId) {

        return jdbcTemplate.query(
                getCartItemsForBooking,
                new Object[] { userId },
                (rs, rowNum) -> {

                    BookingCartItem item = new BookingCartItem();

                    item.setProductId(rs.getString("product_id"));
                   
                    item.setQuantity(rs.getInt("quantity"));
                    item.setProductPrice(rs.getBigDecimal("product_price"));

                    return item;
                }
        );
    }

    // Create parent booking
    public void createBooking(
            String bookingId,
            String userId,
            BigDecimal totalAmount) {

        jdbcTemplate.update(
                createBooking,
                bookingId,
                userId,
                totalAmount,
                "PENDING",
                userId
        );
    }

    // Create individual booking item
    public void createBookingItem(
            String bookingItemId,
            String bookingId,
            String productId,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal totalPrice,
            String userId) {

        jdbcTemplate.update(
                createBookingItem,
                bookingItemId,
                bookingId,
                productId,
                quantity,
                unitPrice,
                totalPrice,
                "CONFIRMED",
                userId
        );
    }

    // Clear user's cart after booking
    public void clearCart(String userId) {

        jdbcTemplate.update(
                clearCart,
                userId
        );
    }
    
    public void updateBookingStatus(String bookingId, String status) {

        jdbcTemplate.update(
                updateBookingStatus,
                status,
                bookingId
        );
    }
    
    public DirectBookingProduct getProductForBooking(String productId) {

        return jdbcTemplate.queryForObject(
                getProductForBooking,
                new Object[]{productId},
                (rs, rowNum) -> {

                    DirectBookingProduct product =
                            new DirectBookingProduct();
                    

                    product.setProductId(
                            rs.getString("product_id"));

                    product.setProductName(
                            rs.getString("product_name"));

                    product.setProductPrice(
                            rs.getBigDecimal("product_price"));

                    product.setAvailableQuantity(
                            rs.getInt("available_quantity"));

                    return product;
                }
        );
    }
}