package app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BookingProductResponse {

    private String productName;
    private Integer quantity;
    private BigDecimal totalPrice;
}