package com.nour.demo.controller;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.nour.demo.service.StatisticsService;

public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }
    @GetMapping("/api/statistics/tutor/{tutorId}")
    public Map<String, Object> getTutorStatistics(
            @PathVariable Long tutorId,
            @RequestParam(required = false, defaultValue = "") String timeRange) {
        return statisticsService.getTutorStatistics(tutorId, timeRange);
    }
     @GetMapping("/api/statistics/earnings")
    public Map<String, Object> getEarningsStatistics(
            @RequestParam(required = false, defaultValue = "") String timeRange) {
        return statisticsService.getEarningsStatistics(timeRange);
    }
    @GetMapping("/api/statistics/courses")
    public Map<String, Object> getCoursesStatistics() {
        return statisticsService.getCoursesStatistics();
    }
    @GetMapping("/api/statistics/ratings")
    public Map<String, Object> getRatingsStatistics() {
        return statisticsService.getRatingsStatistics();
    }
}
