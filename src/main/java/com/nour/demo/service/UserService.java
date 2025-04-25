package com.nour.demo.service;

import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nour.demo.dto.UserRegistrationDTO;
import com.nour.demo.model.User;
import com.nour.demo.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
        
        // Encode the password
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        // Set default status and role
        user.setStatus(User.Status.ACTIVE);
        user.setRoles(Set.of(User.Role.STUDENT));

        // Save the user
        return userRepository.save(user);
    }
}