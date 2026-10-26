package app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class StoreCartResponse {

    private String name;

    private BigDecimal price;
}