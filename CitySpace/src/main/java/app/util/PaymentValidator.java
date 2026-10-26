package app.util;

import org.springframework.stereotype.Component;


import app.dto.PaymentRequest;
import app.exception.InvalidPaymentException;

@Component
public class PaymentValidator {

    public void validate(PaymentRequest request) {

        if (request == null) {
            throw new InvalidPaymentException(
                    "Payment details are required");
        }

        if (request.getPaymentMethod() == null
                || request.getPaymentMethod().isBlank()) {

            throw new InvalidPaymentException(
                    "Payment method is required");
        }

        validateBillingDetails(request);

        switch (request.getPaymentMethod().toUpperCase()) {

            case "CREDIT_CARD":
            case "DEBIT_CARD":

                validateCard(request);
                break;

            case "UPI":

                validateUpi(request);
                break;

            case "RAZORPAY":

                validateRazorpay(request);
                break;

            case "COD":

                validateCod(request);
                break;

            default:

                throw new InvalidPaymentException(
                        "Invalid payment method");
        }
    }

    private void validateCard(
            PaymentRequest request) {

        if (request.getCardNumber() == null
                || !request.getCardNumber()
                        .matches("\\d{16}")) {

            throw new InvalidPaymentException(
                    "Card number must contain 16 digits");
        }

        if (request.getExpiryMonth() == null
                || !request.getExpiryMonth()
                        .matches("(0[1-9]|1[0-2])")) {

            throw new InvalidPaymentException(
                    "Invalid expiry month");
        }

        if (request.getExpiryYear() == null
                || !request.getExpiryYear()
                        .matches("\\d{4}")) {

            throw new InvalidPaymentException(
                    "Invalid expiry year");
        }

        if (request.getCvv() == null
                || !request.getCvv()
                        .matches("\\d{3}")) {

            throw new InvalidPaymentException(
                    "CVV must contain 3 digits");
        }

        // Dummy successful card
        if (!"4111111111111111"
                .equals(request.getCardNumber())) {

            throw new InvalidPaymentException(
                    "Card payment failed");
        }
    }

    private void validateUpi(
            PaymentRequest request) {

        if (request.getUpiId() == null
                || !request.getUpiId()
                        .matches(
                            "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+$")) {

            throw new InvalidPaymentException(
                    "Invalid UPI ID");
        }
    }

    private void validateRazorpay(
            PaymentRequest request) {

        if (request.getRazorpayContact() == null
                || !request.getRazorpayContact()
                        .matches("\\d{10}")) {

            throw new InvalidPaymentException(
                    "Enter a valid 10 digit mobile number");
        }
    }

    private void validateCod(
            PaymentRequest request) {

        // No card or UPI validation required
    }

    private void validateBillingDetails(
            PaymentRequest request) {

        if (request.getBillingEmail() == null
                || !request.getBillingEmail()
                        .matches(
                            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new InvalidPaymentException(
                    "Invalid billing email");
        }

        if (request.getBillingAddress() == null
                || request.getBillingAddress().isBlank()) {

            throw new InvalidPaymentException(
                    "Billing address is required");
        }
    }
}