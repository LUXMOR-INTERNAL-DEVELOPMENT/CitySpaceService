package app.dto;

import java.math.BigDecimal;

import lombok.Data;


@Data
public class EvenFilterRequest {

    private String categoryName;

    private String fromDate;

    private String toDate;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private String location;

    private Double minRating;

    private Double maxRating;

    private String sortBy;
}


