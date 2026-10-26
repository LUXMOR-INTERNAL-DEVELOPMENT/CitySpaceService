package app.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entity.Event;
import app.repository.ReviewRespository;


@Component
public class RatingCalculation {
	@Autowired
	private ReviewRespository reviewRespository;
	
	@Autowired
	private RatingUtil ratingUtil;

    public Map<String, Double> calculateEventRatings(
            List<Event> events) {

        Map<String, Double> averageRatings = new HashMap<>();

        if (events == null || events.isEmpty()) {
            return averageRatings;
        }

        for (Event event : events) {
            String eventId = event.getId();

            List<Double> ratings =
                    reviewRespository.getRatingsByEventId(eventId);

            double averageRating =
                    ratingUtil.calculateAverageRating(ratings);

            averageRatings.put(eventId, averageRating);
        }

        return averageRatings;
    }

    public double calculateEventRating(String eventId) {
        List<Double> ratings =
                reviewRespository.getRatingsByEventId(eventId);

        return ratingUtil.calculateAverageRating(ratings);
    }
}
