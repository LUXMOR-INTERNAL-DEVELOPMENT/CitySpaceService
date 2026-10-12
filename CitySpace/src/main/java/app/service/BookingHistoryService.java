package app.service;

import java.util.List;

import app.dto.BookingHistoryResponse;

public interface BookingHistoryService {

    List<BookingHistoryResponse> getBookingHistory(String userId);
}