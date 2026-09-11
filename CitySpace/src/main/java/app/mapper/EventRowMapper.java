package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.Entity.Event;





@Component
public class EventRowMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {

        Event event = new Event();

        event.setEventId(rs.getString("event_id"));

        event.setVendorId(rs.getString("vendor_id"));

        event.setEventDate(rs.getString("event_date"));

        event.setEventName(rs.getString("event_name"));

        event.setCategoryId(rs.getString("category_id"));

        if (rs.getBytes("event_image") != null) {
            event.setEventImage(
                Base64.getEncoder().encodeToString(
                    rs.getBytes("event_image")
                )
            );
        }

        event.setEventLocation(rs.getString("event_location"));

        event.setEventDescription(rs.getString("event_description"));

        event.setEventStatus(rs.getString("event_status"));

        event.setRating(rs.getDouble("rating"));

        event.setEventPrice(rs.getBigDecimal("event_price"));

        event.setLatitude(rs.getDouble("latitude"));

        event.setLongitude(rs.getDouble("longitude"));

        event.setCategoryName(rs.getString("category_name"));

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