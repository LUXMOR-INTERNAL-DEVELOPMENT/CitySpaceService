
package app.util;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RatingUtil {

    public double calculateAverageRating(List<Double> ratings) {

        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }

        double totalRating = 0.0;
        int count = 0;

        for (Double rating : ratings) {
            if (rating != null) {
                totalRating += rating;
                count++;
            }
        }

        if (count == 0) {
            return 0.0;
        }

        return Math.round((totalRating / count) * 10.0) / 10.0;
    }
}