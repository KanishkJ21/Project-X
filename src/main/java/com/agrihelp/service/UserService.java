package com.agrihelp.service;

import com.agrihelp.model.User;
import com.agrihelp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create new user (with password hashing)
    public User createUser(User user) {
        // Hash password
        user.setPassword(hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    // Verify password
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

    // Find by email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Find by phone
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    // Find by Aadhaar
    public Optional<User> findByAadhaarNumber(String aadhaarNumber) {
        return userRepository.findByAadhaarNumber(aadhaarNumber);
    }

    // New: Find user by ID
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    // New: Update user (save changes)
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // Password hashing utility
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
