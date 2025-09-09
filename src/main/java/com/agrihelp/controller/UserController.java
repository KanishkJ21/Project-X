package com.agrihelp.controller;

import com.agrihelp.model.User;
import com.agrihelp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Signup
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        // Check for existing email, phone, or Aadhaar
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        if (userService.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            return ResponseEntity.badRequest().body("Phone number already registered");
        }
        if (userService.findByAadhaarNumber(user.getAadhaarNumber()).isPresent()) {
            return ResponseEntity.badRequest().body("Aadhaar number already registered");
        }

        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // Login (email, phone, or Aadhaar)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String identifier = loginRequest.get("identifier"); // email / phone / aadhaar
        String password = loginRequest.get("password");

        Optional<User> userOpt = userService.findByEmail(identifier);
        if (userOpt.isEmpty()) userOpt = userService.findByPhoneNumber(identifier);
        if (userOpt.isEmpty()) userOpt = userService.findByAadhaarNumber(identifier);

        if (userOpt.isPresent() && userService.verifyPassword(password, userOpt.get().getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
