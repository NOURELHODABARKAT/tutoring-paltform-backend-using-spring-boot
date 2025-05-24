package com.nour.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nour.demo.dto.AuthDto.AuthRequest;
import com.nour.demo.dto.AuthDto.AuthResponse;
import com.nour.demo.dto.AuthDto.RegisterRequest;
import com.nour.demo.model.User.Role;
import com.nour.demo.model.User.User;
import com.nour.demo.security.JwtUtil;
import com.nour.demo.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        // Authenticate user
        boolean isAuthenticated = userService.authenticate(authRequest.getEmail(), authRequest.getPassword());

        if (isAuthenticated) {
            // Generate JWT token
            Map<String, Object> claims = new HashMap<>();
            Optional<User> userOpt = userService.findByEmail(authRequest.getEmail());

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                claims.put("role", user.getRole());
                claims.put("email", user.getEmail());

                String token = jwtUtil.generateToken(user.getEmail(), claims);
                return ResponseEntity.ok(new AuthResponse(token, user.getEmail()));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest, Role Rolee) {
        // Check if username already exists
        Optional<User> existingUser = userService.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        // Create new user
        User user = new User();
        // Encrypt password with BCrypt before saving
        String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(registerRequest.getPassword(),
                org.mindrot.jbcrypt.BCrypt.gensalt());
        user.setPassword(hashedPassword);
        user.setEmail(registerRequest.getEmail());
        user.setRole(Rolee); // Default role

        User savedUser = userService.saveUser(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, savedUser.getEmail()));
    }
}
// Compare this snippet from src/main/java/com/nour/demo/dto/LoginDTO.java:
// package com.nour.demo.dto;
//