package com.nour.demo.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nour.demo.dto.ProfileCompletionDTO;
import com.nour.demo.dto.UserRegistrationDTO;
import com.nour.demo.model.User.User;
import com.nour.demo.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final ConversionService conversionService;

    public UserController(UserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @GetMapping("/email")
    public String getByEmail(@RequestParam String email) {
        return userService.findByEmail(email)
                .map(User::getEmail)
                .orElse("email not found");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO dto) {
        User user = conversionService.convert(dto, User.class);
        userService.saveUser(user);
        return ResponseEntity.ok("User registered");
    }

    @PutMapping("/{id}/complete-profile")
    public ResponseEntity<?> completeProfile(@PathVariable Long id, @RequestBody ProfileCompletionDTO dto) {
        Optional<User> userOpt = userService.findById(id);

        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        try {
            userService.saveUser(user);
            return ResponseEntity.ok("Profile completed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}