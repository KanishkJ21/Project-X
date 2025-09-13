package com.agrihelp.controller;

import com.agrihelp.model.User;
import com.agrihelp.service.UserService;
import com.agrihelp.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        // Register with encoded password
        return userService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        Optional<User> authenticatedUser = userService.authenticate(user.getUsername(), user.getPassword());

        if (authenticatedUser.isPresent()) {
            // Generate JWT for authenticated user
            String token = jwtUtils.generateToken(authenticatedUser.get().getUsername());
            return Map.of("message", "Login successful", "token", token);
        } else {
            return Map.of("message", "Invalid credentials");
        }
    }
}
