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
@Table(name = "coupon")
public class Coupon {

    // COMMAND:
    // Store the unique ID of the coupon.
    @Id
    @Column(name = "coupon_id")
    private String couponId;
    
    @Column(name = "category_id")
    private String categoryId;
    
    @Column(name = "vendor_id")
    private String vendorId;

    // COMMAND:
    // Store the offer category/name shown in the UI.
    // Example: Dining, Events, Activities, Workshops, Stores.
    private String name;

    // COMMAND:
    // Store the offer description shown in the UI.
    private String description;

    // COMMAND:
    // Store the coupon code shown in the UI.
    @Column(name = "coupon_code")
    private String couponCode;

    // COMMAND:
    // Store the image URL/path for the coupon.
    @Column(name = "image")
    private String image;
    
 // COMMAND:
 // Store the discount text shown on the offer badge.
 // Examples: "20% OFF", "₹200 OFF", "B2G1".
 @Column(name = "discount_display")
 private String discountDisplay;

    // COMMAND:
    // Store the type of discount used internally.
    @Column(name = "discount_type")
    private String discountType;

    // COMMAND:
    // Store the discount value used internally.
    @Column(name = "discount_value")
    private BigDecimal discountValue;

    // COMMAND:
    // Store the minimum booking amount required.
    @Column(name = "minimum_booking_amount")
    private BigDecimal minimumBookingAmount;

    // COMMAND:
    // Store the maximum discount allowed.
    @Column(name = "maximum_discount")
    private BigDecimal maximumDiscount;

    // COMMAND:
    // Store the internal offer calculation type.
    @Column(name = "offer_type")
    private String offerType;

    // COMMAND:
    // Store the date from which the coupon is valid.
    @Column(name = "valid_from")
    private LocalDate validFrom;

    // COMMAND:
    // Store the date until which the coupon is valid.
    @Column(name = "valid_until")
    private LocalDate validUntil;

    // COMMAND:
    // Store whether the coupon is ACTIVE or INACTIVE.
    private String status;

    // COMMAND:
    // Store when the coupon was created.
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // COMMAND:
    // Store who created the coupon.
    @Column(name = "created_by")
    private String createdBy;

    // COMMAND:
    // Store when the coupon was updated.
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // COMMAND:
    // Store who updated the coupon.
    @Column(name = "updated_by")
    private String updatedBy;
}