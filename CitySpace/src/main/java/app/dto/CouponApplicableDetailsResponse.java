package app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CouponApplicableDetailsResponse {

    private String couponCode;
    private String categoryName;
    private String categoryType;
    private String categoryDescription;
    private BigDecimal categoryPrice;
    private BigDecimal offerAmount;
    private BigDecimal totalAmount;
}