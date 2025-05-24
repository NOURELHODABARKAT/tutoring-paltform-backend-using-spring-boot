package com.nour.demo.dto;
import java.util.List;

public class StudentDashboardDTO {

    private List<CourseInfo> enrolledCourses;
    private List<LiveSessionForStudentDTO> upcomingSessions;
    public List<CourseInfo> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<CourseInfo> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public List<LiveSessionForStudentDTO> getUpcomingSessions() {
        return upcomingSessions;
    }

    public void setUpcomingSessions(List<LiveSessionForStudentDTO> upcomingSessions) {
        this.upcomingSessions = upcomingSessions;
    }
    public static class CourseInfo {
        private Long courseId;
        private String courseTitle;
        private String tutorName;
        private int numberOfSessions;

        // Getters and Setters
        public Long getCourseId() {
            return courseId;
        }

        public void setCourseId(Long courseId) {
            this.courseId = courseId;
        }

        public String getCourseTitle() {
            return courseTitle;
        }

        public void setCourseTitle(String courseTitle) {
            this.courseTitle = courseTitle;
        }

        public String getTutorName() {
            return tutorName;
        }

        public void setTutorName(String tutorName) {
            this.tutorName = tutorName;
        }

        public int getNumberOfSessions() {
            return numberOfSessions;
        }

        public void setNumberOfSessions(int numberOfSessions) {
            this.numberOfSessions = numberOfSessions;
        }
    }
}
