package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloworldController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/hello")
    public String showMessage(@RequestParam("username") String username, Model model) {
        // Create a personalized message
        String message = "Hello, " + username + "!";
        model.addAttribute("message", message);
        return "index";  // Return the same page with the message displayed
    }
}
