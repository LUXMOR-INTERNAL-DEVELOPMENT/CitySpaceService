package app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ApplyCouponResponse {

    // COMMAND:
    // Show the original booking amount before applying the coupon.
    private BigDecimal totalAmount;

    // COMMAND:
    // Show the amount discounted by the coupon.
    private BigDecimal offerAmount;

    // COMMAND:
    // Show the final booking amount after applying the coupon.
    private BigDecimal finalAmount;
}