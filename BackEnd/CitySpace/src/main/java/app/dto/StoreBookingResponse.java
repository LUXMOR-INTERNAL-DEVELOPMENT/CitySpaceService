package app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StoreBookingResponse {

    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private Integer availableQuantity;
    private String productImage;

    private Integer quantity;

    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    private BigDecimal gstPercentage;
    private BigDecimal gstAmount;
    private BigDecimal finalAmount;

    private Boolean addToCart;
    private Boolean productRemoved;

    private String bookingStatus;

    private String paymentMethod;
    private String paymentStatus;

    private LocalDateTime bookingDate;
    private LocalDateTime cancelledAt;
}