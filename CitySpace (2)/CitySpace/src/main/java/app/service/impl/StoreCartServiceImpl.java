package app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.StoreCartDao;
import app.dto.StoreCartResponse;
import app.service.StoreCartService;

@Service
public class StoreCartServiceImpl implements StoreCartService {

    @Autowired
    private StoreCartDao storeCartDao;

    @Override
    public void addToCart(String userId, String productId, Integer quantity) {

        storeCartDao.addToCart(userId, productId, quantity);
    }

    @Override
    public List<StoreCartResponse> getCartByUserId(String userId) {

        return storeCartDao.getCartByUserId(userId);
    }

    @Override
    public void updateCartQuantity(String cartId, Integer quantity) {

        storeCartDao.updateCartQuantity(cartId, quantity);
    }

    @Override
    public void deleteCartItem(String cartId) {

        storeCartDao.deleteCartItem(cartId);
    }
}