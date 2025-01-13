package appdev.com.techmatch.controller;

import appdev.com.techmatch.model.UserAccount;
import appdev.com.techmatch.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class sampleController {

    @Autowired
    private UserInfoService userInfoService;

    // Existing endpoints

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String showSignup() {
        return "signup";
    }

    @GetMapping("/home")
    public String showHome() {
        return "home";
    }

    // New endpoints for database integration

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<UserAccount> users = userInfoService.listAll();
        model.addAttribute("users", users);
        return "users"; // Render a 'users.html' template with the list of users
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
        return "redirect:/users";
    }
}
