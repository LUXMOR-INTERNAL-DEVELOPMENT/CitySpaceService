package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.dto.BookingHistoryResponse;
import app.service.BookingHistoryService;
import app.service.impl.BookingHistoryServiceImpl;

@RestController
@RequestMapping("/api/user/store")
public class BookingHistoryController {

    @Autowired
    private BookingHistoryServiceImpl bookingHistoryService;

    @GetMapping("/booking/history/{userId}")
    public List<BookingHistoryResponse> getBookingHistory(
            @PathVariable String userId) {

        return bookingHistoryService.getBookingHistory(userId);
    }
}