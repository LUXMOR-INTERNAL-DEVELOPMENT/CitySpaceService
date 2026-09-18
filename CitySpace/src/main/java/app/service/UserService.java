package app.service;

import java.util.List;

import app.entity.Event;

public interface UserService {

    List<Event> searchEvents(String keyword);

    List<Event> searchEventDetails(String keyword);
}
