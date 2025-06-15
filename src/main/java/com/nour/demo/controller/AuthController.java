package com.nour.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nour.demo.dto.AuthDto.AuthRequest;
import com.nour.demo.dto.AuthDto.RegisterRequest;
import com.nour.demo.model.User.Status;
import com.nour.demo.model.User.User;
import com.nour.demo.security.JwtUtil;
import com.nour.demo.service.UserService;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

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
            // Return token, email, and role in the response
            return ResponseEntity.ok(
                Map.of(
                    "token", token,
                    "email", user.getEmail(),
                    "role", user.getRole().name()
                )
            );
        }
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
}

@PostMapping("/register")
public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
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
    user.setRole(registerRequest.getRole());
    user.setFirstName(registerRequest.getFirstName());
    user.setLastName(registerRequest.getLastName());
    user.setPhoneNumber(registerRequest.getPhoneNumber());
    user.setBirthdate(registerRequest.getBirthdate());
    user.setWilaya(registerRequest.getWilaya());
    user.setStatus(Status.ACTIVE); // or Status.INACTIVE if you want
    User savedUser = userService.saveUser(user);

    // Generate JWT token
    String token = jwtUtil.generateToken(savedUser.getEmail());

    // Return token, email, and role in the response
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of(
                "token", token,
                "email", savedUser.getEmail(),
                "role", savedUser.getRole().name()
            ));
}
@GetMapping("/verify")
public ResponseEntity<?> verify(@RequestHeader("Authorization") String authHeader) {
    // Extract token from header
    String token = authHeader.replace("Bearer ", "");
    String email = jwtUtil.extractEmail(token);

    Optional<User> userOpt = userService.findByEmail(email);
    if (userOpt.isPresent()) {
        User user = userOpt.get();
        // Return user info (customize as needed)
        return ResponseEntity.ok(Map.of(
            "isAuthenticated", true,
            "user", Map.of(
                "email", user.getEmail(),
                "role", user.getRole().name(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName()
            )
        ));
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("isAuthenticated", false));
}
}
// Compare this snippet from src/main/java/com/nour/demo/dto/LoginDTO.java:
// package com.nour.demo.dto;
//