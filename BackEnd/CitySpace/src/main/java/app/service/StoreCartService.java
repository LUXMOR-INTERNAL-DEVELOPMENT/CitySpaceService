package app.service;

import java.util.List;

import app.dto.StoreCartResponse;

public interface StoreCartService {

    void addToCart(String userId, String productId, Integer quantity);

    List<StoreCartResponse> getCartByUserId(String userId);

    void updateCartQuantity(String cartId, Integer quantity);

    void deleteCartItem(String cartId);
}