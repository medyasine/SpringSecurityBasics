package com.example.demo.controller;

import ch.qos.logback.core.model.Model;
import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class AuthController {
    private UserService userService;
    public AuthController(UserService userService) { this.userService = userService; }
    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Map<String, Object> model){
        UserDTO user = new UserDTO();
        model.put("user", user);
        return "register";
    }
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDTO,
                               BindingResult result, Map<String, Object> model){

        System.out.println("UserDTO: "+userDTO);
        User existingUser = userService.findByEmail(userDTO.getEmail());
        if(existingUser != null && existingUser.getEmail() != null &&
                !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,"There is already an account registered with the same email");
        }
        if(result.hasErrors()){
            model.put("user", userDTO);
            return "/register";
        }
        userService.save(userDTO);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Map<String, Object> model){
        List<UserDTO> users = userService.findAllUsers();
        model.put("users", users);
        return "users";
    }
}
