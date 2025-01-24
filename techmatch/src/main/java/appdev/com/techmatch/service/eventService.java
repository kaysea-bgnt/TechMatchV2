package appdev.com.techmatch.service;

import org.springframework.stereotype.Service;
import appdev.com.techmatch.model.Event;
import appdev.com.techmatch.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event) {
        event.setEventID(generateCustomEventID());
        return eventRepository.save(event);
    }

    private String generateCustomEventID() {
        long count = eventRepository.count();
        return String.format("EVENT-%05d", count + 1);
    }
    
}
