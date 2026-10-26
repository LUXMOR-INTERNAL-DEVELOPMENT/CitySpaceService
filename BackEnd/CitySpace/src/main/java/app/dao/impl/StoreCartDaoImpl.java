package app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.dao.StoreCartDao;
import app.dto.StoreCartResponse;
import app.repository.StoreCartRepository;

@Repository
public class StoreCartDaoImpl implements StoreCartDao {

    @Autowired
    private StoreCartRepository storeCartRepository;

    @Override
    public void addToCart(String userId, String productId, Integer quantity) {

        storeCartRepository.addToCart(userId, productId, quantity);
    }

    @Override
    public List<StoreCartResponse> getCartByUserId(String userId) {

        return storeCartRepository.getCartByUserId(userId);
    }

    @Override
    public void updateCartQuantity(String cartId, Integer quantity) {

        storeCartRepository.updateCartQuantity(cartId, quantity);
    }

    @Override
    public void deleteCartItem(String cartId) {

        storeCartRepository.deleteCartItem(cartId);
    }
}