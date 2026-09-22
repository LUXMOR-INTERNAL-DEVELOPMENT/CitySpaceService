package app.dto;


import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiningBookingResponse {

    private String bookingId;
    private String eventName;
    private String bookingDate;
    private String slotId;
    private String slotStartTime;
    private String slotEndTime;
    private int numberOfGuests;
    private String bookingStatus;

    private String paymentId;
    private BigDecimal bookingAmount;
    private BigDecimal gstAmount;
    private BigDecimal convenienceFee;
    private BigDecimal totalAmount;
    private String paymentStatus;
}
