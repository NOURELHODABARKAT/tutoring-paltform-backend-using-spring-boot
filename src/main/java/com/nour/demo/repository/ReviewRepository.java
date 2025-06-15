package com.nour.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nour.demo.dto.ReviewDTO;

@Repository
public class ReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addReview(ReviewDTO reviewDTO) {
        String sql = "INSERT INTO reviews (user_id, course_id, rating, comment) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, reviewDTO.getUserId(), reviewDTO.getCourseId(), reviewDTO.getRating(), reviewDTO.getComment());
    }

    public List<Map<String, Object>> getReviewsByCourseId(Integer courseId) {
        String sql = "SELECT * FROM reviews WHERE course_id = ? ORDER BY review_date DESC";
        return jdbcTemplate.queryForList(sql, courseId);
    }

    public boolean userExists(Integer userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }

    public boolean courseExists(Integer courseId) {
    String sql = "SELECT COUNT(*) FROM courses WHERE course_id = ?";
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, courseId);
    return count != null && count > 0;
}

    
    public Double getAverageRating() {
        String sql = "SELECT AVG(rating) FROM reviews";
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM reviews";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }
}
