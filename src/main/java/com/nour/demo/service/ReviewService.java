package com.nour.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.tutor.demo.dto.ReviewDTO;
import com.example.tutor.demo.repository.ReviewRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void addReview(ReviewDTO reviewDTO) {
        // Validate rating
        if (reviewDTO.getRating() < 1 || reviewDTO.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        // Verify user exists
        if (!reviewRepository.userExists(reviewDTO.getUserId())) {
            throw new IllegalArgumentException("User does not exist");
        }

        // Verify course exists
        if (!reviewRepository.courseExists(reviewDTO.getCourseId())) {
            throw new IllegalArgumentException("Course does not exist");
        }

        // Delegate to repository
        reviewRepository.addReview(reviewDTO);
    }

    public List<Map<String, Object>> getReviewsByCourseId(Integer courseId) {
        return reviewRepository.getReviewsByCourseId(courseId);
    }
}