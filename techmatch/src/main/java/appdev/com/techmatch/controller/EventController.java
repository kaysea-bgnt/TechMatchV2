package appdev.com.techmatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import appdev.com.techmatch.model.Event;
import appdev.com.techmatch.service.eventService;
import appdev.com.techmatch.repository.EventRepository; // Import your repository
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/events")
public class EventController {

    private final eventService eventService;
    //private final EventRepository eventRepository; // Add EventRepository field

    // Constructor injection for dependencies
    public EventController(eventService eventService, EventRepository eventRepository) {
        this.eventService = eventService;
        //this.eventRepository = eventRepository;
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<Event> events = eventService.getAllEvents();
        // Convert image binary data to base64
        for (Event event : events) {
            if (event.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(event.getImage());
                event.setBase64Image(base64Image);
            }
        }
        model.addAttribute("events", events);
        return "home"; // Name of your HTML template for the home page
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(
            @RequestParam String name,
            @RequestParam LocalDate startDate,
            @RequestParam LocalTime startTime,
            @RequestParam LocalDate endDate,
            @RequestParam LocalTime endTime,
            @RequestParam String location,
            @RequestParam String description,
            @RequestParam String ticketType,
            @RequestParam boolean requiresApproval,
            @RequestParam int capacity,
            @RequestParam String type,
            @RequestParam List<String> topics, // Expect a list of topics directly
            @RequestParam("image") MultipartFile image // Image as MultipartFile
    ) {
        // Create and save event
        Event event = new Event();
        event.setName(name);
        event.setStartDate(startDate);
        event.setStartTime(startTime);
        event.setEndDate(endDate);
        event.setEndTime(endTime);
        event.setLocation(location);
        event.setDescription(description);
        event.setTicketType(ticketType);
        event.setRequiresApproval(requiresApproval);
        event.setCapacity(capacity);
        event.setType(type);
        event.setTopics(topics);

        try {
            // Handle image upload
            if (!image.isEmpty()) {
                event.setImage(image.getBytes());
            }

            eventService.saveEvent(event);
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/home").body("Event submitted successfully!"); // Redirect to home page
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving event");
        }
    }

}
