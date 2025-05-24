package com.nour.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nour.demo.dto.LiveSessionCreationDTO;
import com.nour.demo.dto.LiveSessionForStudentDTO;
import com.nour.demo.dto.LiveSessionResponseDTO;
import com.nour.demo.dto.StudentDashboardDTO;
import com.nour.demo.dto.StudentDashboardDTO.CourseInfo;
import com.nour.demo.model.Cours;
import com.nour.demo.model.LiveSession;
import com.nour.demo.model.User.User;
import com.nour.demo.repository.CourseRepository;
import com.nour.demo.repository.LiveSessionRepository;
import com.nour.demo.repository.UserRepository;

@Service
public class LiveSessionService {

    private final LiveSessionRepository liveSessionRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public LiveSessionService(
            LiveSessionRepository liveSessionRepository,
            UserRepository userRepository,
            CourseRepository courseRepository) {
        this.liveSessionRepository = liveSessionRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public ResponseEntity<LiveSessionResponseDTO> createLiveSession(LiveSessionCreationDTO sessionDTO) {
        Optional<User> tutor = userRepository.findById(sessionDTO.getTutorId());
        Optional<Cours> course = courseRepository.findById(sessionDTO.getCourseId());

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

    private LiveSession convertToEntity(LiveSessionCreationDTO dto, User tutor, Cours course) {
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

    public List<LiveSessionForStudentDTO> getSessionsForStudentCourses(Long studentId) {
        Optional<User> studentOpt = userRepository.findById(studentId);

        if (studentOpt.isEmpty()) {
            return List.of();
        }

        User student = studentOpt.get();

        Set<Cours> enrolledCourses = student.getCourses();

        if (enrolledCourses.isEmpty()) {
            return List.of();
        }

        List<LiveSession> sessions = liveSessionRepository.findAll().stream()
                .filter(session -> enrolledCourses.contains(session.getCourse()))
                .toList();

        return sessions.stream().map(session -> {
            LiveSessionForStudentDTO dto = new LiveSessionForStudentDTO();
            dto.setId(session.getId());
            dto.setTitle(session.getTitle());
            dto.setDescription(session.getDescription());
            dto.setStartTime(session.getStartTime());
            dto.setDurationInMinutes(session.getDurationInMinutes());
            dto.setMeetingLink(session.getMeetingLink());
            // dto.setCourseTitle(session.getCourse().getTitle());
            return dto;
        }).toList();
    }

    public StudentDashboardDTO getStudentDashboard(Long studentId) {
        Optional<User> studentOpt = userRepository.findById(studentId);

        if (studentOpt.isEmpty()) {
            return new StudentDashboardDTO();
        }

        User student = studentOpt.get();
        List<CourseInfo> courses = student.getCourses().stream()
                .map(course -> {
                    CourseInfo info = new CourseInfo();
                    info.setCourseId(course.getId().longValue());
                    info.setCourseTitle(course.getTitle());
                    info.setTutorName(course.getTutor().getFirstName() + " " + course.getTutor().getLastName());
                    info.setNumberOfSessions(
                            (int) liveSessionRepository.findByCourseId(course.getId().longValue()).stream().count());
                    return info;
                })
                .toList();
        List<LiveSessionForStudentDTO> sessions = getSessionsForStudentCourses(studentId);

        StudentDashboardDTO dashboard = new StudentDashboardDTO();
        dashboard.setEnrolledCourses(courses);
        dashboard.setUpcomingSessions(sessions);

        return dashboard;
    }
}