package app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductUpdateRequest {

    @NotBlank(message = "Vendor ID is required")
    private String vendorId;

    @NotBlank(message = "Subcategory ID is required")
    private String subcategoryId;

    @NotBlank(message = "Product name is required")
    private String productName;

    private String shortDescription;

    private String productDescription;

    @NotBlank(message = "SKU is required")
    private String sku;

    private String brand;

    private LocalDate productDate;

    private String productLocation;

    private String productTags;

    private String productType;

    private String productImage;

    @NotNull(message = "Original price is required")
    @DecimalMin(value = "0.01", message = "Original price must be greater than 0")
    private BigDecimal originalPrice;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.01", message = "Product price must be greater than 0")
    private BigDecimal productPrice;

    @NotNull(message = "Discount percentage is required")
    @DecimalMin(value = "0.0", message = "Discount cannot be negative")
    @DecimalMax(value = "100.0", message = "Discount cannot exceed 100")
    private BigDecimal discountPercentage;

    @NotBlank(message = "Stock status is required")
    private String stockStatus;

    @NotNull(message = "Available quantity is required")
    @Min(value = 0, message = "Available quantity cannot be negative")
    private Integer availableQuantity;

    private String status;
}