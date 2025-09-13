package com.agrihelp.service;

import com.agrihelp.model.User;
import com.agrihelp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Registers a new user with hashed password.
     *
     * @param username Username of the user
     * @param email    Email of the user
     * @param password Plain text password
     * @return Saved User object
     */
    public User registerUser(String username, String email, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, hashedPassword);
        return userRepository.save(user);
    }

    /**
     * Authenticates a user with username and password.
     *
     * @param username Username of the user
     * @param password Plain text password
     * @return Optional containing User if authentication is successful, empty otherwise
     */
    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return userOpt;
        }

        return Optional.empty();
    }
}
