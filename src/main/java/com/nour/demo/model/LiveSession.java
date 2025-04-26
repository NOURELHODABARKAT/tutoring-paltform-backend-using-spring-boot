package com.nour.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class LiveSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    private String description;
    
    private LocalDateTime startTime;
    
    private Integer durationInMinutes;
    
    private Integer availableSeats;
    
    private String meetingLink;
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private User tutor; 
    @ManyToOne
    @JoinColumn(name = "course_id")
    private courese course; 
    @ManyToMany
    @JoinTable(
        name = "live_session_students",
        joinColumns = @JoinColumn(name = "session_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> enrolledStudents = new HashSet<>();
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    public courese getCourse() {
        return course;
    }

    public void setCourse(courese course) {
        this.course = course;
    }

    public Set<User> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(Set<User> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}
