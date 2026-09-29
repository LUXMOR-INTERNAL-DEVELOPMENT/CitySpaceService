package app.dto;

import lombok.Data;

@Data
public class DeliveryDetailsResponse {

    
    private String recipientName;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String deliveryStatus;
}