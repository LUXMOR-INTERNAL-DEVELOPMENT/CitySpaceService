package app.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.dto.DeliveryDetailsRequest;
import app.dto.DeliveryDetailsResponse;

@Repository
public class DeliveryDetailsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${store.saveDeliveryDetails}")
    private String saveDeliveryDetails;
    
    @Value("${store.checkBookingOwnership}")
    private String checkBookingOwnership;

    public DeliveryDetailsResponse saveDeliveryDetails(
            DeliveryDetailsRequest request,
            String userId) {

        // Generate internal delivery ID
        String deliveryId =
                "DEL-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        // Save delivery details
        jdbcTemplate.update(
                saveDeliveryDetails,
                deliveryId,
                request.getBookingId(),
                request.getRecipientName(),
                request.getPhoneNumber(),
                request.getAddress(),
                request.getCity(),
                request.getState(),
                request.getPincode(),
                "PENDING",
                userId
        );
        

        // Prepare response
        DeliveryDetailsResponse response =
                new DeliveryDetailsResponse();

    
        response.setRecipientName(
                request.getRecipientName());

        response.setPhoneNumber(
                request.getPhoneNumber());

        response.setAddress(
                request.getAddress());

        response.setCity(
                request.getCity());

        response.setState(
                request.getState());

        response.setPincode(
                request.getPincode());

        response.setDeliveryStatus(
                "PENDING");

        return response;
    }
    
    public boolean isBookingOwnedByUser(
            String bookingId,
            String userId) {

        Integer count = jdbcTemplate.queryForObject(
                checkBookingOwnership,
                new Object[] { bookingId, userId },
                Integer.class);

        return count != null && count > 0;
    }
}