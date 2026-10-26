package app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StoreLogoUpdateRequest {

    @NotBlank(message = "Store logo is required")
    private String storeLogo;
}