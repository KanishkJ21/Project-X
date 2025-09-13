package com.agrihelp.repository;

import com.agrihelp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides basic CRUD operations and custom queries for username and email.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return Optional containing the user if found, otherwise empty
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their email.
     *
     * @param email the email to search for
     * @return Optional containing the user if found, otherwise empty
     */
    Optional<User> findByEmail(String email);
}
