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
@NoArgsConstructor // Required for Hibernate
@AllArgsConstructor // Add this for builder/all-args
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
    private Set<User> enrolledStudents = new HashSet<>();

    @Column(name = "is_free")
    @Builder.Default
    private boolean isFree = false;
}