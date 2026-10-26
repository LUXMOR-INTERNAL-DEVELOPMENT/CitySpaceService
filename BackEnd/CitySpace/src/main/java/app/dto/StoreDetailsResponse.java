package app.dto;

import java.util.List;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDetailsResponse {
	
	 @JsonIgnore
    private String storeId;
	 
    private String storeName;
    private String storeLogo;
    private String storeLocation;
    private String storeCategory;
    private String eventDescription;

    private List<Map<String, Object>> eventCatalogue;

    private String offer;

    private RatingSummary ratingSummary;
}