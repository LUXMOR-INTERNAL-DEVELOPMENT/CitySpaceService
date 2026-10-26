package app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.BookingHistoryDao;
import app.dto.BookingHistoryResponse;
import app.service.BookingHistoryService;

@Service
public class BookingHistoryServiceImpl implements BookingHistoryService {

    @Autowired
    private BookingHistoryDao bookingHistoryDao;

    @Override
    public List<BookingHistoryResponse> getBookingHistory(String userId) {

        return bookingHistoryDao.getBookingHistory(userId);
    }
}