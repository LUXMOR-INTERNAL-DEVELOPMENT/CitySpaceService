package app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateBookingRequest {

    @NotBlank(message = "User ID is required")
    private String userId;
}