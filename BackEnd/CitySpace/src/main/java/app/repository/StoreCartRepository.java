package app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.dto.StoreCartResponse;

@Repository
public class StoreCartRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${store.addToCart}")
    private String addToCart;

    @Value("${store.getCartByUserId}")
    private String getCartByUserId;

    @Value("${store.updateCartQuantity}")
    private String updateCartQuantity;

    @Value("${store.deleteCartItem}")
    private String deleteCartItem;

    public void addToCart(String userId, String productId, Integer quantity) {

        jdbcTemplate.update(
                addToCart,
                userId,
                productId,
                quantity,
                userId
        );
    }

    public List<StoreCartResponse> getCartByUserId(String userId) {

        return jdbcTemplate.query(
                getCartByUserId,
                new Object[] { userId },
                (rs, rowNum) -> {

                    StoreCartResponse response = new StoreCartResponse();

                    response.setName(rs.getString("product_name"));
                    response.setPrice(rs.getBigDecimal("product_price"));

                    return response;
                }
        );
    }

    public void updateCartQuantity(String cartId, Integer quantity) {

        jdbcTemplate.update(
                updateCartQuantity,
                quantity,
                cartId
        );
    }

    public void deleteCartItem(String cartId) {

        jdbcTemplate.update(
                deleteCartItem,
                cartId
        );
    }
}