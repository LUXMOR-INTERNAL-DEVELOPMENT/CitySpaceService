package app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductVariantRequest {

    private String variantId;

    private String size;

    private String color;

    private String variantSku;

    private BigDecimal variantPrice;

    private Integer variantStock;
}