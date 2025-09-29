package com.agrihelp.controller;

import com.agrihelp.model.User;
import com.agrihelp.service.UserService;
import com.agrihelp.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    // ✅ Register
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        if (userService.userExists(user.getUsername(), user.getEmail())) {
            return Map.of("message", "Username or email already exists");
        }

        User newUser = userService.registerUser(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );

        return Map.of(
                "message", "User registered successfully",
                "userId", newUser.getId(),
                "username", newUser.getUsername(),
                "email", newUser.getEmail(),
                "role", newUser.getRole()
        );
    }

    // ✅ Get all users (Admin only ideally)
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // ✅ Delete user
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return Map.of("message", "User deleted successfully", "deletedUserId", id);
    }

    // ✅ Login
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        try {
            Optional<User> authenticatedUser = userService.authenticate(user.getUsername(), user.getPassword());

            if (authenticatedUser.isPresent()) {
                User loggedInUser = authenticatedUser.get();
                String token = jwtUtils.generateToken(loggedInUser.getUsername());

                return Map.of(
                        "message", "Login successful",
                        "token", token,
                        "userId", loggedInUser.getId(),
                        "username", loggedInUser.getUsername(),
                        "email", loggedInUser.getEmail(),
                        "role", loggedInUser.getRole()
                );
            } else {
                return Map.of("message", "Invalid credentials");
            }
        } catch (RuntimeException e) {
            return Map.of("message", e.getMessage());
        }
    }

    // ✅ Get logged-in user info (for dashboard)
    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(@RequestHeader("Authorization") String token) {
        String username = jwtUtils.extractUsername(token.substring(7));
        Optional<User> userOpt = userService.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return Map.of(
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "role", user.getRole(),
                    "approved", user.getApproved()
            );
        }
        return Map.of("message", "User not found");
    }
}
