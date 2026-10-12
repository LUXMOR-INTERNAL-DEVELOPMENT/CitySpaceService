package app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StoreNameUpdateRequest {

    @NotBlank(message = "Store name is required")
    private String storeName;

    @NotBlank(message = "Store URL is required")
    private String storeUrl;
}