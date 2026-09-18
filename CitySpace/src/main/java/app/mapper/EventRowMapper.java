package app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import app.entity.Event;

@Component
public class EventRowMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {

        Event event = new Event();

        event.setId(rs.getString("id"));
        event.setEventName(rs.getString("event_name"));
        event.setEventLocation(rs.getString("event_location"));
        event.setEventDate(rs.getString("event_date"));
        event.setPrice(rs.getDouble("price"));
        event.setLatitude(rs.getObject("latitude", Double.class));
        event.setLongitude(rs.getObject("longitude", Double.class));
        event.setEventDescription(rs.getString("event_description"));
        event.setStatus(rs.getString("status"));
        event.setLatitude(rs.getObject("latitude", Double.class));
        event.setLongitude(rs.getObject("longitude", Double.class));

        return event;
    }
}