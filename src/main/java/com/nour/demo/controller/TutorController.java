package com.nour.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nour.demo.dto.TutorProfileDTO;
import com.nour.demo.service.TutorService;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @GetMapping("/{tutorId}/profile")
    public ResponseEntity<TutorProfileDTO> getTutorProfile(@PathVariable int tutorId) {
        TutorProfileDTO profile = (TutorProfileDTO) tutorService.getTutorProfile(tutorId);
        return ResponseEntity.ok(profile);
    }
}