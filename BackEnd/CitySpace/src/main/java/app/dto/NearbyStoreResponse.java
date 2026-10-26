package app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NearbyStoreResponse {

    private String storeName;
    private String storeLogo;
    private String storeLocation;
    private String storeCategory;
    private String offer;
    private Double latitude;
    private Double longitude;
    private Double distance;
}