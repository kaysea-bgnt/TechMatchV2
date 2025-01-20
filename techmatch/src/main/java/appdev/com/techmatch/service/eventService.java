package appdev.com.techmatch.service;

import appdev.com.techmatch.model.Event;
import appdev.com.techmatch.repository.EventRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class eventService {

    private final EventRepository eventRepository;

    public eventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }
    
}