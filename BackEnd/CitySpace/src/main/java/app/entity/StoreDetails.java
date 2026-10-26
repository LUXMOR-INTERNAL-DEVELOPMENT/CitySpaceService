package app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "store_details")
public class StoreDetails {

    @Id
    @Column(name = "store_id")
    private String storeId;

    @Column(name = "vendor_id", nullable = false)
    private String vendorId;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "store_url")
    private String storeUrl;

    @Column(name = "store_logo")
    private String storeLogo;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "about_store")
    private String aboutStore;

    @Column(name = "store_keywords")
    private String storeKeywords;

    @Column(name = "store_location")
    private String storeLocation;

    @Column(name = "store_category")
    private String storeCategory;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "catalogue", columnDefinition = "JSON")
    private List<String> catalogue;


    @Column(name = "offers")
    private String offers;

    @Column(name = "rating", nullable = false)
    private BigDecimal rating;

    @Column(name = "review_count", nullable = false)
    private Integer reviewCount;

    @Column(name = "rating_details")
    private String ratingDetails;

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