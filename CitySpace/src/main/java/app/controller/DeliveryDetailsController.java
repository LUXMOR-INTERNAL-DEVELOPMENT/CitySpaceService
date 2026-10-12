package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import app.dto.DeliveryDetailsRequest;
import app.dto.DeliveryDetailsResponse;
import app.service.impl.DeliveryDetailsServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@Validated
@RequestMapping("/api/user/store/delivery")
public class DeliveryDetailsController {

    @Autowired
    private DeliveryDetailsServiceImpl deliveryDetailsService;

    @PostMapping
    public ResponseEntity<DeliveryDetailsResponse> saveDeliveryDetails(
            @Valid @RequestBody DeliveryDetailsRequest request,
            @RequestParam
            @NotBlank(message = "User ID is required")
            String userId) {

        DeliveryDetailsResponse response =
                deliveryDetailsService.saveDeliveryDetails(
                        request,
                        userId);

        return ResponseEntity.ok(response);
    }
}