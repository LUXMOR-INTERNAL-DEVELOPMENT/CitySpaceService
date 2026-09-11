package app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import app.Entity.Event;
import app.mapper.EventRowMapper;



@Repository
public class EventRepository {
	@Autowired
	private  JdbcTemplate jdbcTemplate;

    @Value("${event.getAllByLocation}")
    private String getAllByLocationQuery;

    @Value("${event.getPopularEventsByRating}")
    private String getPopularEventsByRatingQuery;

    @Value("${event.getTopWeekendEventsByLocation}")
    private String getTopWeekendEventsByLocationQuery;
    
    @Autowired
    private EventRowMapper eventRowMapper;
    
    public List<Event> getAllByLocation(
            double latitude,
            double longitude,
            double radius) {

        return jdbcTemplate.query(
                getAllByLocationQuery,
                eventRowMapper,
                latitude,
                longitude,
                latitude,
                radius
        );
    }

    public List<Event> getPopularEventsByRating(
            double latitude,
            double longitude,
            double radius) {

        return jdbcTemplate.query(
                getPopularEventsByRatingQuery,
                eventRowMapper,
                latitude,
                longitude,
                latitude,
                radius
        );
    }

    public List<Event> getTopWeekendEventsByLocation(
            double latitude,
            double longitude,
            double radius) {

        return jdbcTemplate.query(
                getTopWeekendEventsByLocationQuery,
                eventRowMapper,
                latitude,
                longitude,
                latitude,
                radius
        );
    }

}
