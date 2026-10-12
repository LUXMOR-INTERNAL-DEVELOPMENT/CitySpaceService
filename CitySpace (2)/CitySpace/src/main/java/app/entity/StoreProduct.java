package app.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "store_product")
public class StoreProduct {

    @Id
    @Column(name = "product_id")
    private String productId;
    
    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "subcategory_id", nullable = false)
    private String subcategoryId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "sku")
    private String sku;

    @Column(name = "brand")
    private String brand;

    @Column(name = "product_date")
    private LocalDate productDate;

    @Column(name = "product_location")
    private String productLocation;

    @Column(name = "product_tags")
    private String productTags;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "product_price", nullable = false)
    private BigDecimal productPrice;

    @Column(name = "original_price")
    private BigDecimal originalPrice;

    @Column(name = "discount_percentage")
    private BigDecimal discountPercentage;

    @Column(name = "stock_status")
    private String stockStatus;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}