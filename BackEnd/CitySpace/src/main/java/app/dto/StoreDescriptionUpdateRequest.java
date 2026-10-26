package app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StoreDescriptionUpdateRequest {

    @NotBlank(message = "Short description is required")
    private String shortDescription;

    @NotBlank(message = "About store is required")
    private String aboutStore;

    @NotBlank(message = "Store keywords are required")
    private String storeKeywords;
}