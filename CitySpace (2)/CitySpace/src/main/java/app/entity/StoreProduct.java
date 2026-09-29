package app.entity;

import java.math.BigDecimal;
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

    @Column(name = "subcategory_id", nullable = false)
    private String subcategoryId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_price", nullable = false)
    private BigDecimal productPrice;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    @Column(name = "product_image")
    private String productImage;

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
