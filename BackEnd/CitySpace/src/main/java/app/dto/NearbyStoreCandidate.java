package app.dto;

import lombok.Data;

@Data
public class NearbyStoreCandidate {

    private String storeId;

    private String storeName;
    private String storeLogo;
    private String storeLocation;
    private String storeCategory;
    private String offer;

    private Double latitude;
    private Double longitude;
}