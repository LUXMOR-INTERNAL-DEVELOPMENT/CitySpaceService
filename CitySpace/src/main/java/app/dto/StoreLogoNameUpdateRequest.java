package app.dto;

import lombok.Data;

@Data
public class StoreLogoNameUpdateRequest {

    private String storeName;
    private String storeLogo;
    private String updatedBy;
}