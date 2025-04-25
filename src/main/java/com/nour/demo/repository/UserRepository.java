package com.nour.demo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nour.demo.model.User;

/**
 * Repository interface for accessing user data from the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email.
     *
     * @param email the email to search for
     * @return an Optional containing the user if found, or empty if not
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user exists with the specified email.
     *
     * @param email the email to check
     * @return true if a user exists, false otherwise
     */
    Boolean existsByEmail(String email);

    /**
     * Checks if a user exists with the specified phone number.
     *
     * @param phoneNumber the phone number to check
     * @return true if a user exists, false otherwise
     */
    Boolean existsByPhoneNumber(String phoneNumber);
}
    
}
