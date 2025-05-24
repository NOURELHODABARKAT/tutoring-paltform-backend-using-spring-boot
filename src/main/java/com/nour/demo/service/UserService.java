package com.nour.demo.service;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.nour.demo.dto.UserRegistrationDTO;
import com.nour.demo.model.User.Role;
import com.nour.demo.model.User.Status;
import com.nour.demo.model.User.User;
import com.nour.demo.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        return userRepository.save(user);
    }

    public User registerUser(UserRegistrationDTO dto) {
        // Check if email is already registered
        if (findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Map DTO to User entity using ModelMapper
        User user = modelMapper.map(dto, User.class);

        // Encode the password using BCrypt
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));

        // Set default status and role
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.STUDENT);

        // Save the user
        return userRepository.save(user);
    }
    public boolean authenticate(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Compare the raw password with the encoded one
            return BCrypt.checkpw(rawPassword, user.getPassword());
        }
        return false;
    }
}