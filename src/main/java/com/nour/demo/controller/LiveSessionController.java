package com.nour.demo.controller;

import com.nour.demo.dto.LiveSessionCreationDTO;
import com.nour.demo.dto.LiveSessionResponseDTO;
import com.nour.demo.service.LiveSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class LiveSessionController {

    private final LiveSessionService liveSessionService;

    public LiveSessionController(LiveSessionService liveSessionService) {
        this.liveSessionService = liveSessionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<LiveSessionResponseDTO> createSession(@RequestBody LiveSessionCreationDTO dto) {
        return liveSessionService.createLiveSession(dto);
    }

    @GetMapping
    public ResponseEntity<List<LiveSessionResponseDTO>> getAllSessions() {
        return ResponseEntity.ok(liveSessionService.getAllSessions());
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<LiveSessionResponseDTO>> getSessionsByTutor(@PathVariable Long tutorId) {
        return ResponseEntity.ok(liveSessionService.getSessionsByTutor(tutorId));
    }
}