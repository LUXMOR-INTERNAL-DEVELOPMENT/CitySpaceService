package app.dto;

import lombok.Data;

@Data
public class AvailableTable {

    private String tableId;

    private String vendorId;

    private String tableNumber;

    private int capacity;

    private String tableStatus;
}