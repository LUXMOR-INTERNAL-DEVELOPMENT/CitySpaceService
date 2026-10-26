package app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplyCouponRequest {

  
    // COMMAND:
    // Identify the booking on which the coupon is applied.
    @NotBlank(message = "Booking ID is required")
    private String bookingId;

    // COMMAND:
    // Identify the selected coupon.
    @NotBlank(message = "Coupon ID is required")
    private String couponId;
}