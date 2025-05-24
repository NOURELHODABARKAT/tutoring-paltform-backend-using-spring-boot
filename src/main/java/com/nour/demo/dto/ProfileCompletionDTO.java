package com.nour.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.nour.demo.model.User.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCompletionDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDateTime birthdate;
    private Role roles;
    private String bio;
    private String profilePicture;

    // // Getters and Setters

    // public String getFirstName() {
    // return firstName;
    // }

    // public void setFirstName(String firstName) {
    // this.firstName = firstName;
    // }

    // public String getLastName() {
    // return lastName;
    // }

    // public void setLastName(String lastName) {
    // this.lastName = lastName;
    // }

    // public String getPhoneNumber() {
    // return phoneNumber;
    // }

    // public void setPhoneNumber(String phoneNumber) {
    // this.phoneNumber = phoneNumber;
    // }

    // public String getDateOfBirth() {
    // return dateOfBirth;
    // }

    // public void setDateOfBirth(String dateOfBirth) {
    // this.dateOfBirth = dateOfBirth;
    // }

    // public Set<Role> getRoles() {
    // return roles;
    // }

    // public void setRoles(Set<Role> roles) {
    // this.roles = roles;
    // }

    // public String getBio() {
    // return bio;
    // }

    // public void setBio(String bio) {
    // this.bio = bio;
    // }

    // public String getProfilePicture() {
    // return profilePicture;
    // }

    // public void setProfilePicture(String profilePicture) {
    // this.profilePicture = profilePicture;
    // }
}
