package com.nour.demo.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nour.demo.repository.CourseRepository;
import com.nour.demo.repository.PaymentRepository;
import com.nour.demo.repository.ReviewRepository;
import com.nour.demo.repository.UserRepository;

@Service
public class StatisticsService {

    private final CourseRepository coursRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    // قيمة أرباح ثابتة (static) حسب طلبك، يمكن تعديلها لأي قيمة تريدين
    private static final BigDecimal STATIC_TOTAL_EARNINGS = new BigDecimal("12345.67");

    public StatisticsService(CourseRepository coursRepository, 
                             PaymentRepository paymentRepository, 
                             UserRepository userRepository,
                             ReviewRepository reviewRepository) {
        this.coursRepository = coursRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    public Map<String, Object> getTutorStatistics(Long tutorId, String timeRange) {
        Map<String, Object> stats = new HashMap<>();

        long coursesCount = coursRepository.countByTutorId(tutorId);
        long studentsCount = userRepository.countStudentsByTutorId(tutorId);

        BigDecimal totalEarnings = STATIC_TOTAL_EARNINGS;

        stats.put("coursesCount", coursesCount);
        stats.put("studentsCount", studentsCount);
        stats.put("totalEarnings", totalEarnings);

        return stats;
    }

    public Map<String, Object> getEarningsStatistics(String timeRange) {
        Map<String, Object> stats = new HashMap<>();

        
        BigDecimal totalEarnings = STATIC_TOTAL_EARNINGS;

        stats.put("totalEarnings", totalEarnings);
        return stats;
    }

    public Map<String, Object> getCoursesStatistics() {
        Map<String, Object> stats = new HashMap<>();

        long totalCourses = coursRepository.count();

        stats.put("totalCourses", totalCourses);
        return stats;
    }

    public Map<String, Object> getRatingsStatistics() {
        Map<String, Object> stats = new HashMap<>();

        Double averageRating = reviewRepository.getAverageRating();
        long totalRatings = reviewRepository.count();

        stats.put("averageRating", averageRating != null ? averageRating : 0.0);
        stats.put("totalRatings", totalRatings);

        return stats;
    }
}
