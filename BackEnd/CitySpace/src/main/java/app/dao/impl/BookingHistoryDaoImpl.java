package app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.dao.BookingHistoryDao;
import app.dto.BookingHistoryResponse;
import app.repository.BookingHistoryRepository;

@Repository
public class BookingHistoryDaoImpl implements BookingHistoryDao {

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    @Override
    public List<BookingHistoryResponse> getBookingHistory(String userId) {

        return bookingHistoryRepository.getBookingHistoryByUserId(userId);
    }
}