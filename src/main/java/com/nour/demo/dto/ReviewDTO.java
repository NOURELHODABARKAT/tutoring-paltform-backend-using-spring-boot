package com.nour.demo.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewDTO {

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Course ID is required")
    private Integer courseId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be greater than 5")
    private Integer rating;

    @Size(max = 255, message = "Comment must be at most 255 characters")
    private String comment;

    public ReviewDTO() {
    }

    public ReviewDTO(Integer userId, Integer courseId, Integer rating, String comment) {
        this.userId = userId;
        this.courseId = courseId;
        this.rating = rating;
        this.comment = comment;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}