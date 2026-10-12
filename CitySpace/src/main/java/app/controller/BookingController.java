package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.dto.BookingResponse;
import app.dto.CreateBookingRequest;
import app.dto.DirectBookingRequest;
import app.service.impl.BookingServiceImpl;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/user/store/booking")
public class BookingController {

    @Autowired
    private BookingServiceImpl bookingService;

    // Cart Checkout
    @PostMapping
    public ResponseEntity<String> createBooking(
            @Valid @RequestBody CreateBookingRequest request) {

        bookingService.createBooking(request.getUserId());

        return ResponseEntity.ok(
                "Booking created successfully");
    }


    // Direct Book Now
    @PostMapping("/direct")
    public ResponseEntity<BookingResponse> createDirectBooking(
            @Valid @RequestBody DirectBookingRequest request) {

        BookingResponse response =
                bookingService.createDirectBooking(request);

        return ResponseEntity.ok(response);
    }
}