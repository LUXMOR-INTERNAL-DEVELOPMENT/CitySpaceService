package app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BookingHistoryResponse {

    private String name;

    private String description;

    private BigDecimal price;
}