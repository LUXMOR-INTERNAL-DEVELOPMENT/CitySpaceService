package app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DirectBookingProduct {

    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer availableQuantity;
}