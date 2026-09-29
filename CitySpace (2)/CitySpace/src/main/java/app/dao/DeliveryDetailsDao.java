package app.dao;

import app.dto.DeliveryDetailsRequest;
import app.dto.DeliveryDetailsResponse;

public interface DeliveryDetailsDao {
	
	  boolean isBookingOwnedByUser(
	            String bookingId,
	            String userId);

	    DeliveryDetailsResponse saveDeliveryDetails(
	            DeliveryDetailsRequest request,
	            String userId);
    
   
}