package app.dao;

import java.util.List;

import app.dto.BookingHistoryResponse;

public interface BookingHistoryDao {

    List<BookingHistoryResponse> getBookingHistory(String userId);
    
   
}