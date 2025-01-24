package appdev.com.techmatch.controller;

import appdev.com.techmatch.model.User;
import appdev.com.techmatch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signUp(@ModelAttribute User user, Model model) {
        String email = user.getEmail();

        // Email validation
        if (email == null || email.isEmpty()) {
            model.addAttribute("error", "Email cannot be empty");
            return "signup"; // return to the signup page
        }

        // Check if email ends with "@iskolarngbayan.pup.edu.ph"
        if (!email.toLowerCase().endsWith("@iskolarngbayan.pup.edu.ph")) {
            model.addAttribute("error", "Enter a valid PUP webmail.");
            return "signup"; // return to the signup page
        }

        // Additional regex to ensure it's a valid email format (basic check)
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            model.addAttribute("error", "Please enter a valid email address");
            model.addAttribute("user", user);  // Pass the User object back to pre-fill the form
            return "signup"; // return to the signup page with error
        }

        // If validation passes, save the user
        userService.saveUser(user);
        return "redirect:/login"; // redirect to login page after successful signup
    }
    
}
