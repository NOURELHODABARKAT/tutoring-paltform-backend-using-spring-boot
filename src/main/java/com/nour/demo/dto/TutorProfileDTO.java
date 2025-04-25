package com.nour.demo.dto;

import java.util.List;
import java.util.Map;

public class TutorProfileDTO {
    private List<Map<String, Object>> courses;
    private Double averageRating;
    private List<Map<String, Object>> comments;

    public TutorProfileDTO() {
    }

    public TutorProfileDTO(List<Map<String, Object>> courses, Double averageRating, List<Map<String, Object>> comments) {
        this.courses = courses;
        this.averageRating = averageRating;
        this.comments = comments;
    }

    public List<Map<String, Object>> getCourses() {
        return courses;
    }

    public void setCourses(List<Map<String, Object>> courses) {
        this.courses = courses;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public List<Map<String, Object>> getComments() {
        return comments;
    }

    public void setComments(List<Map<String, Object>> comments) {
        this.comments = comments;
    }
}