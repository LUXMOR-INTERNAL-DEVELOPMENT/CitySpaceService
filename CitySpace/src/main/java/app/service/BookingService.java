package app.service;

import app.dto.BookingResponse;
import app.dto.DirectBookingProduct;
import app.dto.DirectBookingRequest;

public interface BookingService {

    void createBooking(String userId);
   

    BookingResponse createDirectBooking(
            DirectBookingRequest request);
    
    

}