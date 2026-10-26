package app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class StoreProductResponse {

    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private Integer availableQuantity;
    private String productImage;
}