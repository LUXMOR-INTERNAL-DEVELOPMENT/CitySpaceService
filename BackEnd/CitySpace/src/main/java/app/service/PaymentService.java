package app.service;

import app.dto.PaymentResponse;
import app.dto.PaymentRequest;

public interface PaymentService {

    PaymentResponse makePayment(PaymentRequest request);

}