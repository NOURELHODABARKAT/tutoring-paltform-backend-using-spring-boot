package com.nour.demo.controller;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nour.demo.dto.LoginDTO;
import com.nour.demo.dto.SignupDTO;
import com.nour.demo.model.User;
import com.nour.demo.model.User.Status;
import com.nour.demo.repository.UserRepository;
import com.nour.demo.security.JwtToken;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtToken jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtToken jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@Valid @RequestBody SignupDTO signupDTO) {
        if (userRepository.existsByEmail(signupDTO.getEmail())) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Email already exists");
        }

        User user = new User();
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setStatus(Status.INACTIVE);

        userRepository.save(user);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    dto.getEmail(),
                    dto.getPassword()
                )
            );

            String token = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok("Bearer " + token);

        } catch (BadCredentialsException ex) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email or password");
        } catch (Exception ex) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Authentication error: " + ex.getMessage());
        }
    }
}
// Compare this snippet from src/main/java/com/nour/demo/dto/LoginDTO.java:
// package com.nour.demo.dto;
//