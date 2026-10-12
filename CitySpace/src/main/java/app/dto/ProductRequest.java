package app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ProductRequest {

    private String productId;
    
    private String vendorId;

    private String subcategoryId;

    private String productName;

    private String shortDescription;

    private String productDescription;

    private String sku;

    private String brand;

    private LocalDate productDate;

    private String productLocation;

    private String productTags;

    private String productType;

    private String productImage;

    private BigDecimal originalPrice;

    private BigDecimal productPrice;

    private BigDecimal discountPercentage;

    private String stockStatus;

    private Integer availableQuantity;

    private BigDecimal rating;

    private Integer reviewCount;

    private String status;

    private String createdBy;

    private List<ProductVariantRequest> variants;
}