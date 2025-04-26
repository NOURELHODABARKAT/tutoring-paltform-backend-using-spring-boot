package com.nour.demo.service;

import com.nour.demo.dto.LiveSessionCreationDTO;
import com.nour.demo.dto.LiveSessionResponseDTO;
import com.nour.demo.model.LiveSession;
import com.nour.demo.model.User;
import com.nour.demo.model.courese;
import com.nour.demo.repository.LiveSessionRepository;
import com.nour.demo.repository.CourseRepository;
import com.nour.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ResponseEntity<LiveSessionResponseDTO> createLiveSession(LiveSessionCreationDTO sessionDTO) {
        Optional<User> tutor = userRepository.findById(sessionDTO.getTutorId());
        Optional<courese> course = courseRepository.findById(sessionDTO.getCourseId());

        if (tutor.isEmpty() || course.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LiveSession session = convertToEntity(sessionDTO, tutor.get(), course.get());
        LiveSession savedSession = liveSessionRepository.save(session);
        
        return new ResponseEntity<>(convertToDTO(savedSession), HttpStatus.CREATED);
    }

    public List<LiveSessionResponseDTO> getAllSessions() {
        return liveSessionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<LiveSessionResponseDTO> getSessionsByTutor(Long tutorId) {
        return liveSessionRepository.findByTutorId(tutorId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private LiveSession convertToEntity(LiveSessionCreationDTO dto, User tutor, courese course) {
        return LiveSession.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startTime(dto.getStartTime())
                .durationInMinutes(dto.getDurationInMinutes())
                .availableSeats(dto.getAvailableSeats())
                .meetingLink(dto.getMeetingLink())
                .tutor(tutor)
                .course(course)
                .build();
    }

    private LiveSessionResponseDTO convertToDTO(LiveSession session) {
        return LiveSessionResponseDTO.builder()
                .id(session.getId())
                .title(session.getTitle())
                .description(session.getDescription())
                .startTime(session.getStartTime())
                .durationInMinutes(session.getDurationInMinutes())
                .availableSeats(session.getAvailableSeats())
                .meetingLink(session.getMeetingLink())
                .tutorId(session.getTutor().getId())
                .courseId(session.getCourse().getId().longValue())
                .build();
    }
}