package app.service;

import app.dto.DeliveryDetailsRequest;
import app.dto.DeliveryDetailsResponse;

public interface DeliveryDetailsService {

    DeliveryDetailsResponse saveDeliveryDetails(
            DeliveryDetailsRequest request,
            String userId);
}