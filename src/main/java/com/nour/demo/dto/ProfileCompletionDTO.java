package com.nour.demo.dto;

import java.util.Set;

import com.nour.demo.model.Role;
import com.nour.demo.model.User;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProfileCompletionDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String dateOfBirth;
    private Set<Role> roles;
  
    private String bio;
    private String profilePicture;
}