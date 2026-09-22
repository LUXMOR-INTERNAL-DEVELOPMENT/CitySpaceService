package app.dto;

import lombok.Data;

@Data
public class BookingTableAllocation {

    private String bookingTableAllocationId;

    private String bookingId;

    private String tableId;

    private String slotId;

    private String bookingDate;

    private int numberOfGuests;

    private String bookingStatus;
}