package app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Checkout type is required")
    private String checkoutType;

    // Required only for DIRECT checkout
    private String productId;

    private Integer quantity;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    // Delivery details
    @NotBlank(message = "Recipient name is required")
    private String recipientName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Pincode is required")
    private String pincode;
}