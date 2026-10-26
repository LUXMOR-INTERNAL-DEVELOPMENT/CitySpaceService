package app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@JsonPropertyOrder({
    "recommendedEvents",
    "overallEvents",
    "popularEvents",
    "upcomingEvents",
    "weekendEvents"
})
@Data
@NoArgsConstructor
@AllArgsConstructor

public class HomeResponse {

 

    private List<EventResponse> overallEvents;

    private List<EventResponse> popularEvents;

    private List<UpcomingEventResponse> upcomingEvents;

    private List<EventResponse> weekendEvents;
    
    private List<EventResponse> recommendedEvents;
}

