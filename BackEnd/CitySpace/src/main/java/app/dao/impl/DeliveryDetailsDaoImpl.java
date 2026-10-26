package app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.dao.DeliveryDetailsDao;
import app.dto.DeliveryDetailsRequest;
import app.dto.DeliveryDetailsResponse;
import app.repository.DeliveryDetailsRepository;

@Repository
public class DeliveryDetailsDaoImpl implements DeliveryDetailsDao {

    @Autowired
    private DeliveryDetailsRepository deliveryDetailsRepository;

    @Override
    public boolean isBookingOwnedByUser(
            String bookingId,
            String userId) {

        return deliveryDetailsRepository.isBookingOwnedByUser(
                bookingId,
                userId);
    }

    @Override
    public DeliveryDetailsResponse saveDeliveryDetails(
            DeliveryDetailsRequest request,
            String userId) {

        return deliveryDetailsRepository.saveDeliveryDetails(
                request,
                userId);
    }
}