package com.nour.demo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nour.demo.model.User.User;



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
    long countByRole(String role);
@Query("SELECT COUNT(u) FROM User u JOIN u.courses c WHERE c.tutor.id = :tutorId AND u.role = 'STUDENT'")
long countStudentsByTutorId(@Param("tutorId") Long tutorId);
}
    

