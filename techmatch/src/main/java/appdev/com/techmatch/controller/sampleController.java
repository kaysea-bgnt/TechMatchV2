package appdev.com.techmatch.controller;

import appdev.com.techmatch.model.Event;
import appdev.com.techmatch.model.UserAccount;
import appdev.com.techmatch.service.UserInfoService;
import appdev.com.techmatch.service.eventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class sampleController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private eventService eventService;

    // Existing endpoints

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String showSignup() {
        return "signup";
    }

    // New endpoints for database integration

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<UserAccount> users = userInfoService.listAll();
        model.addAttribute("users", users);
        return "users"; // Render a 'users.html' template with the list of users. Need to replace this with the database? i think?
    }

    @PostMapping("/signup")
    public String createUser(@RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String username,
                             Model model) {
        // Create a new user
        UserAccount newUser = new UserAccount();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setUsername(username);
    
        // Save user to database
        userInfoService.CreateUser(newUser);
    
        // Redirect to the users page or a success page
        return "home";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        // Delete the user by ID
        userInfoService.deleteUser(id);

        // Redirect to the users list
        return "home";
    }

    @GetMapping("/popup")
    public String showPopup(){
        return "popup";
    }

    @GetMapping("/CreateEvent")
    public String ShowCreateEvent() {
        return "CreateEvent";
    }

    @GetMapping("/create")
    public String createEvent() {
        return "create";
    }


    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<Event> events = eventService.getAllEvents();
        //convert image binary data to base64
        for (Event event : events) {
            if (event.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(event.getImage());
                event.setBase64Image(base64Image);
            }
        }

        model.addAttribute("events", events);
        return "home"; // Name of your HTML template for the home page
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    
    
}
