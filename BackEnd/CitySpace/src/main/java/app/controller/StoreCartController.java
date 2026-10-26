package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.dto.StoreCartRequest;
import app.dto.StoreCartResponse;
import app.dto.UpdateCartQuantityRequest;
import app.service.StoreCartService;
import app.service.impl.StoreCartServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@Validated
@RequestMapping("/api/user/store/cart")
public class StoreCartController {

    @Autowired
    private StoreCartServiceImpl storeCartService;

    // Add product to cart
    @PostMapping
    public ResponseEntity<String> addToCart(
            @Valid @RequestBody StoreCartRequest request) {

        storeCartService.addToCart(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity()
        );

        return ResponseEntity.ok(
                "Product added to cart successfully"
        );
    }

    // Get all products in user's cart
    @GetMapping("/{userId}")
    public ResponseEntity<List<StoreCartResponse>> getCart(
            @PathVariable
            @NotBlank(message = "User ID is required")
            String userId) {

        List<StoreCartResponse> response =
                storeCartService.getCartByUserId(userId);

        return ResponseEntity.ok(response);
    }

    // Update cart quantity
    @PutMapping("/{cartId}")
    public ResponseEntity<String> updateCartQuantity(
            @PathVariable
            @NotBlank(message = "Cart ID is required")
            String cartId,
            @Valid @RequestBody UpdateCartQuantityRequest request) {

        storeCartService.updateCartQuantity(
                cartId,
                request.getQuantity()
        );

        return ResponseEntity.ok(
                "Cart quantity updated successfully"
        );
    }

    // Delete product from cart
    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteCartItem(
            @PathVariable
            @NotBlank(message = "Cart ID is required")
            String cartId) {

        storeCartService.deleteCartItem(cartId);

        return ResponseEntity.ok(
                "Product removed from cart successfully"
        );
    }
}