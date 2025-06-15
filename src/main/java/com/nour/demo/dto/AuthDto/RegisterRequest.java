package com.nour.demo.dto.AuthDto;

import java.time.LocalDate;

import com.nour.demo.model.User.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String password;
    private String email;
     private Role role;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthdate;
    private String wilaya;

}
