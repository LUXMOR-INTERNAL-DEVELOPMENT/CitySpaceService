package app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class BookingResponse {

    private String bookingId;
    private BigDecimal bookingAmount;
    private BigDecimal gstPercentage;
    private BigDecimal gstAmount;
    private BigDecimal totalAmount;
    private String bookingStatus;
    private LocalDateTime bookingDate;
    private List<BookingProductResponse> products;
    private DeliveryDetailsResponse deliveryDetails;
}