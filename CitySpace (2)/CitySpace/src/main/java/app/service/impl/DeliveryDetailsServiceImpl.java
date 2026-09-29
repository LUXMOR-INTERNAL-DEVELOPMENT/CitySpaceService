package app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.DeliveryDetailsDao;
import app.dto.DeliveryDetailsRequest;
import app.dto.DeliveryDetailsResponse;
import app.exception.ResourceNotFoundException;
import app.service.DeliveryDetailsService;

@Service
public class DeliveryDetailsServiceImpl
        implements DeliveryDetailsService {

    @Autowired
    private DeliveryDetailsDao deliveryDetailsDao;

    @Override
    public DeliveryDetailsResponse saveDeliveryDetails(
            DeliveryDetailsRequest request,
            String userId) {

        boolean bookingBelongsToUser =
                deliveryDetailsDao.isBookingOwnedByUser(
                        request.getBookingId(),
                        userId);

        if (!bookingBelongsToUser) {
            throw new ResourceNotFoundException(
                    "Booking not found for this user");
        }

        return deliveryDetailsDao.saveDeliveryDetails(
                request,
                userId);
    }
}