package appdev.com.techmatch.controller;

import appdev.com.techmatch.model.Event;
import appdev.com.techmatch.model.Topic;
import appdev.com.techmatch.service.EventService;
import appdev.com.techmatch.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import appdev.com.techmatch.repository.TopicRepository;

import java.io.IOException;

@Controller
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private TopicRepository topicRepository;

    @PostMapping("/create")
    public String createEvent(
        @ModelAttribute Event event,
        @RequestParam("eventTopics") String[] eventTopics, // Collect selected topics
        @RequestParam("imageFile") MultipartFile imageFile,
        @RequestParam("userID") String userID
    ) throws IOException {
        // Combine selected topics into a comma-separated string
    
        // Link the user to the event
        User user = new User();
        user.setUserID(userID);
        event.setUser(user);
    
        // Process the uploaded image
        if (!imageFile.isEmpty()) {
            event.setEventImage(imageFile.getBytes());
        }


        // Process event topics
        for (String topicName : eventTopics) {
            // Check if topic already exists
            Topic topic = topicRepository.findByName(topicName);
            if (topic == null) {
                // Create a new topic if it doesn't exist
                topic = new Topic();
                topic.setName(topicName);
                topicRepository.save(topic);
            }
            event.getTopics().add(topic);
        }

        eventService.saveEvent(event);
    
    
        return "redirect:/home";
    }
    

    @GetMapping("/create")
    public String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "create-event"; // Render the event creation form
    }
}

