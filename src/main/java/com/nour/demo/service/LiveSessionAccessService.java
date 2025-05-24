package com.nour.demo.service;

import org.springframework.stereotype.Service;

@Service
public class LiveSessionAccessService {
    public boolean canAccessSession(Long studentId, Long sessionId) {
        // Dummy implementation: allow access if both IDs are not null and positive
        if (studentId == null || sessionId == null) {
            return false;
        }
        return studentId > 0 && sessionId > 0;
    }
}
