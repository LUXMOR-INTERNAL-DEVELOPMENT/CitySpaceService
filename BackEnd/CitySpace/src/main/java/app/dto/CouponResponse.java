package app.dto;

import lombok.Data;

@Data
public class CouponResponse {

    // COMMAND:
    // Show the coupon category/name in the UI.
    // Example: Dining, Events, Activities, Stores.
    private String name;

    // COMMAND:
    // Show the offer description in the UI.
    // Example: On your first booking.
    private String description;

    // COMMAND:
    // Show the coupon code in the UI.
    // Example: WELCOME20.
    private String couponCode;

    // COMMAND:
    // Show the image file name in the UI.
    // Example: welcome20.png.
    private String image;

    // COMMAND:
    // Show the discount text displayed on the coupon card.
    // Examples: 20% OFF, ₹200 OFF.
    private String discountDisplay;
    
    private String categoryId;
}