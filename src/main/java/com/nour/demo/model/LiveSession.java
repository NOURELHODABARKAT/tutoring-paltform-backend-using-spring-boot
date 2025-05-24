package com.nour.demo.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.nour.demo.model.User.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor 
// Required for Hibernate
@AllArgsConstructor
@Builder
@Entity
@Table(name = "live_sessions")
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
    @JoinColumn(name = "user_id")
    private User tutor;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Cours course;

    @ManyToMany
    @JoinTable(name = "live_session_students", 
               joinColumns = @JoinColumn(name = "session_id"), 
               inverseJoinColumns = @JoinColumn(name = "student_id"))
    @Builder.Default
    private Set<User> enrolledStudents = new HashSet<>(); // Removed final

    @Column(name = "is_free")
    @Builder.Default
    private boolean isFree = false; // Removed final

    // Getters and Setters
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

    public Cours getCourse() {
        return course;
    }

    public void setCourse(Cours course) {
        this.course = course;
    }

    public Set<User> getEnrolledStudents() {
        return enrolledStudents;
    }

    public boolean isFree() {
        return isFree;
    }
}