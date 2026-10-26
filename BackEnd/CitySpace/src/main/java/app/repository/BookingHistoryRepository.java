package app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import app.dto.BookingHistoryResponse;

@Repository
public class BookingHistoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${store.getBookingHistoryByUserId}")
    private String getBookingHistoryByUserId;
    
    @Value("${store.insertBookingHistory}")
    private String insertBookingHistory;

    public List<BookingHistoryResponse> getBookingHistoryByUserId(String userId) {

        return jdbcTemplate.query(
                getBookingHistoryByUserId,
                new Object[] { userId },
                (rs, rowNum) -> {

                    BookingHistoryResponse response =
                            new BookingHistoryResponse();

                    response.setName(rs.getString("name"));
                    response.setDescription(rs.getString("description"));
                    response.setPrice(rs.getBigDecimal("price"));

                    return response;
                }
        );
    }
    
    public void insertBookingHistory(
            String bookingId,
            String userId) {

        jdbcTemplate.update(
                insertBookingHistory,
                bookingId,
                userId
        );
    }
}