package com.nour.demo.service;

import org.springframework.stereotype.Service;

import com.example.tutor.demo.dto.TutorProfileDTO;
import com.example.tutor.demo.repository.TutorRepository;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public TutorProfileDTO getTutorProfile(int tutorId) {
        TutorProfileDTO profile = new TutorProfileDTO();

        profile.setCourses(tutorRepository.getCoursesByTutorId(tutorId));

        Double avgRating = tutorRepository.getAverageRating(tutorId);
        profile.setAverageRating(avgRating != null ? avgRating : 0.0);

        profile.setComments(tutorRepository.getComments(tutorId));

        return profile;
    }
}