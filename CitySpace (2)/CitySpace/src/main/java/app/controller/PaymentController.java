package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import app.dto.PaymentRequest;
import app.dto.PaymentResponse;
import app.service.impl.PaymentServiceImpl;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/user/store/payment")
public class PaymentController {

    @Autowired
    private PaymentServiceImpl paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response = paymentService.makePayment(request);

        return ResponseEntity.ok(response);
    }
}