package com.nour.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TutorRepository {
    private final JdbcTemplate jdbcTemplate;

    public TutorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Retrieve courses by tutor ID
    public List<Map<String, Object>> getCoursesByTutorId(int tutorId) {
        String sql = "SELECT id, title, price, level FROM courses WHERE tutor_id = ?";
        return jdbcTemplate.queryForList(sql, tutorId);
    }

    // Calculate average rating across all tutor's courses
    public Double getAverageRating(int tutorId) {
        String sql = """
            SELECT AVG(r.rating)
            FROM reviews r
            JOIN courses c ON r.course_id = c.id
            WHERE c.tutor_id = ?
            """;
        return jdbcTemplate.queryForObject(sql, Double.class, tutorId);
    }

    // Retrieve all comments for tutor's courses
    public List<Map<String, Object>> getComments(int tutorId) {
        String sql = """
            SELECT c.title AS course_title, r.comment, r.rating, r.review_date
            FROM reviews r
            JOIN courses c ON r.course_id = c.id
            WHERE c.tutor_id = ?
            """;
        return jdbcTemplate.queryForList(sql, tutorId);
    }
}