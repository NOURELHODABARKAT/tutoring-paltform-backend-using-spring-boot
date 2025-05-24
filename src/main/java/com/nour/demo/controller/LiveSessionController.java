package com.nour.demo.controller;

import com.nour.demo.dto.LiveSessionCreationDTO;
import com.nour.demo.dto.LiveSessionResponseDTO;
import com.nour.demo.dto.StudentDashboardDTO;
import com.nour.demo.dto.LiveSessionForStudentDTO;
import com.nour.demo.service.LiveSessionService;
import com.nour.demo.service.LiveSessionAccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class LiveSessionController {

    private final LiveSessionService liveSessionService;
    private final LiveSessionAccessService liveSessionAccessService;

    public LiveSessionController(LiveSessionService liveSessionService,
            LiveSessionAccessService liveSessionAccessService) {
        this.liveSessionService = liveSessionService;
        this.liveSessionAccessService = liveSessionAccessService;
    }

    // @PostMapping
    // @PreAuthorize("hasRole('TUTOR')")
    // public ResponseEntity<LiveSessionResponseDTO> createSession(@RequestBody LiveSessionCreationDTO dto) {
    //     return liveSessionService.createLiveSession(dto);
    // }

    @GetMapping
    public ResponseEntity<List<LiveSessionResponseDTO>> getAllSessions() {
        return ResponseEntity.ok(liveSessionService.getAllSessions());
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<LiveSessionResponseDTO>> getSessionsByTutor(@PathVariable Long tutorId) {
        return ResponseEntity.ok(liveSessionService.getSessionsByTutor(tutorId));
    }

    // @GetMapping("/student-sessions/{studentId}")
    // @PreAuthorize("hasRole('STUDENT')")
    // public ResponseEntity<List<LiveSessionForStudentDTO>> getSessionsForStudentCourses(@PathVariable Long studentId) {
    //     return ResponseEntity.ok(liveSessionService.getSessionsForStudentCourses(studentId));
    // }

    // @GetMapping("/dashboard/{studentId}")
    // @PreAuthorize("hasRole('STUDENT')")
    // public ResponseEntity<StudentDashboardDTO> getStudentDashboard(@PathVariable Long studentId) {
    //     return ResponseEntity.ok(liveSessionService.getStudentDashboard(studentId));
    // }

    @GetMapping("/access/{studentId}/{sessionId}")
    public ResponseEntity<?> canStudentAccessSession(@PathVariable Long studentId, @PathVariable Long sessionId) {
        boolean canAccess = liveSessionAccessService.canAccessSession(studentId, sessionId);
        if (canAccess) {
            return ResponseEntity.ok("Access granted ");
        } else {
            return ResponseEntity.status(403).body("Access denied  - Payment required");
        }
    }

}