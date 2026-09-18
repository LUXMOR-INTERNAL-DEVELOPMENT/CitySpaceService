package app.mapper;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.entity.Event;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EventRowMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {

        Event event = new Event();

        event.setEventId(rs.getString("event_id"));
        event.setVendorId(rs.getString("vendor_id"));

        // Category
        event.setCategoryId(rs.getString("category_id"));
        event.setCategoryName(rs.getString("category_name"));

        // Event Details
        event.setEventName(rs.getString("event_name"));
        event.setEventDescription(rs.getString("event_description"));

        // Event Date - Entity uses LocalDate
        if (rs.getDate("event_date") != null) {
            event.setEventDate(
                rs.getDate("event_date").toLocalDate()
            );
        }

        // Location
        event.setEventLocation(rs.getString("event_location"));
        event.setLatitude(rs.getBigDecimal("latitude"));
        event.setLongitude(rs.getBigDecimal("longitude"));

        // Price
        event.setEventPrice(rs.getBigDecimal("event_price"));

        // Image
        event.setEventImage(rs.getString("event_image"));

        // Status
        event.setStatus(rs.getString("status"));

        // Audit - Entity uses LocalDateTime
        if (rs.getTimestamp("created_at") != null) {
            event.setCreatedAt(
                rs.getTimestamp("created_at").toLocalDateTime()
            );
        }

        event.setCreatedBy(rs.getString("created_by"));

        if (rs.getTimestamp("updated_at") != null) {
            event.setUpdatedAt(
                rs.getTimestamp("updated_at").toLocalDateTime()
            );
        }

        event.setUpdatedBy(rs.getString("updated_by"));

        return event;
    }
}