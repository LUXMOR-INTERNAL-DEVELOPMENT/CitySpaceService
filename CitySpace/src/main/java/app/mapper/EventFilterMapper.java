package app.mapper;

import java.util.List;

import org.springframework.stereotype.Component;


import app.dto.EvenFilterRequest;
import app.entity.Event;

@Component
public class EventFilterMapper {

    public List<Event> filter(
            List<Event> events,
            EvenFilterRequest filter) {

        return events.stream()

                // Category
                .filter(event ->
                        filter.getCategoryName() == null ||
                        (event.getCategoryName() != null &&
                        event.getCategoryName()
                                .equalsIgnoreCase(filter.getCategoryName())))

                // From Date
                .filter(event ->
                        filter.getFromDate() == null ||
                        event.getEventDate()
                                .compareTo(filter.getFromDate()) >= 0)

                // To Date
                .filter(event ->
                        filter.getToDate() == null ||
                        event.getEventDate()
                                .compareTo(filter.getToDate()) <= 0)

                // Minimum Price
                .filter(event ->
                        filter.getMinPrice() == null ||
                        event.getEventPrice()
                                .compareTo(filter.getMinPrice()) >= 0)

                // Maximum Price
                .filter(event ->
                        filter.getMaxPrice() == null ||
                        event.getEventPrice()
                                .compareTo(filter.getMaxPrice()) <= 0)

                // Location
                .filter(event ->
                        filter.getLocation() == null ||
                        (event.getEventLocation() != null &&
                        event.getEventLocation()
                                .toLowerCase()
                                .contains(filter.getLocation().toLowerCase())))

                // Minimum Rating
                .filter(event ->
                        filter.getMinRating() == null ||
                        event.getRating() >= filter.getMinRating())

                // Maximum Rating
                .filter(event ->
                        filter.getMaxRating() == null ||
                        event.getRating() <= filter.getMaxRating())

                // Sorting
                .sorted((e1, e2) -> {

                    if ("PRICE_LOW_TO_HIGH"
                            .equalsIgnoreCase(filter.getSortBy())) {

                        return e1.getEventPrice()
                                .compareTo(e2.getEventPrice());
                    }

                    if ("PRICE_HIGH_TO_LOW"
                            .equalsIgnoreCase(filter.getSortBy())) {

                        return e2.getEventPrice()
                                .compareTo(e1.getEventPrice());
                    }

                    return e1.getEventDate()
                            .compareTo(e2.getEventDate());
                })
                .toList();
    }
}
