package app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DiningBookingRequest {

    private int numberOfGuests;

    private String bookingDate;

    private String slotId;

    private BigDecimal baseAmount;
}