package com.nour.demo.service;

import com.nour.demo.dto.LiveSessionCreationDTO;
import com.nour.demo.model.LiveSession;
import com.nour.demo.model.User;
import com.nour.demo.model.courese; // تم الحفاظ على الاسم كما هو
import com.nour.demo.repository.LiveSessionRepository;
import com.nour.demo.repository.CourseRepository;
import com.nour.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiveSessionService {

    private final LiveSessionRepository liveSessionRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public LiveSessionService(
        LiveSessionRepository liveSessionRepository,
        UserRepository userRepository,
        CourseRepository courseRepository
    ) {
        this.liveSessionRepository = liveSessionRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public ResponseEntity<String> createLiveSession(LiveSessionCreationDTO sessionDTO) {
      
        Optional<User> tutor = userRepository.findById(sessionDTO.getTutorId());
        Optional<courese> course = courseRepository.findById(sessionDTO.getCourseId()); 
        
        if (tutor.isEmpty() || course.isEmpty()) {
            return new ResponseEntity<>("Tutor or Course not found", HttpStatus.NOT_FOUND);
        }

        LiveSession session = new LiveSession();
        session.setTitle(sessionDTO.getTitle());
        session.setStartTime(sessionDTO.getStartTime());
        session.setDuration(sessionDTO.getDuration());
        session.setTutor(tutor.get());
        session.setCourse(course.get()); 
        
        liveSessionRepository.save(session);
        
        return new ResponseEntity<>("Session created successfully!", HttpStatus.CREATED);
    }

    public List<LiveSession> getAllSessions() {
        return liveSessionRepository.findAll();
    }

    public List<LiveSession> getSessionsByTutor(Long tutorId) {
        return liveSessionRepository.findByTutorId(tutorId);
    }
}