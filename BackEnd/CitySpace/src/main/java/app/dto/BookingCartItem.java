package app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BookingCartItem {

    private String productId;
    private String productName;
    private Integer quantity;
    private BigDecimal productPrice;
}