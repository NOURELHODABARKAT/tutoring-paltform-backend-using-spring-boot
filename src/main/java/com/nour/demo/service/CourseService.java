package com.nour.demo.service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nour.demo.dto.CourseDTO;
import com.nour.demo.dto.EnrollmentDTO;
import com.nour.demo.model.Cours;
import com.nour.demo.model.User.Role;
import com.nour.demo.model.User.User;
import com.nour.demo.repository.CourseRepository;
import com.nour.demo.repository.UserRepository;

@Transactional
@Service
public class CourseService {

    private static final String COURSE_NOT_FOUND = "Course not found";

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public Cours createCourse(CourseDTO dto, User currentUser) throws AccessDeniedException {
        if (currentUser.getRole() != Role.TUTOR) {
            throw new AccessDeniedException("Only tutors can publish courses");
        }
        Cours course = new Cours();
        course.setTitle(dto.getTitle());
        course.setCategory(dto.getCategory());
        course.setLevel(dto.getLevel());
        course.setPrice(dto.getPrice());
        course.setDuration(dto.getDuration());
        course.setTutor(currentUser);
        course.setStatus("draft");
        return courseRepository.save(course);
    }

    public ResponseEntity<String> updateCourse(Long id, CourseDTO dto) {
        Optional<Cours> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        Cours course = optionalCourse.get();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());
        courseRepository.save(course);
        return new ResponseEntity<>("Course updated", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteCourse(Long id) {
        Optional<Cours> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        courseRepository.delete(optionalCourse.get());
        return new ResponseEntity<>("Course deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> publishCourse(Long id) {
        Optional<Cours> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
        Cours course = optionalCourse.get();
        course.setStatus("published");
        courseRepository.save(course);
        return new ResponseEntity<>("Course published", HttpStatus.OK);
    }

    public ResponseEntity<String> enrollStudent(EnrollmentDTO enrollmentDTO) {
        Optional<Cours> courseOpt = courseRepository.findByIdWithEnrolledStudents(enrollmentDTO.getCourseId());
        if (courseOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No course found with ID: " + enrollmentDTO.getCourseId());
        }
        Optional<User> studentOpt = userRepository.findById(enrollmentDTO.getStudentId());
        if (studentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No student found with ID: " + enrollmentDTO.getStudentId());
        }
        Cours course = courseOpt.get();
        User student = studentOpt.get();
        // Prevent duplicate enrollment
        if (course.getEnrolledStudents().contains(student)) {
            return ResponseEntity.badRequest().body("Student already enrolled in this course.");
        }
        course.getEnrolledStudents().add(student);
        courseRepository.save(course);
        return ResponseEntity.ok("Student enrolled successfully!");
    }

    public List<Cours> getCoursesByStudentId(Long studentId) {
        return courseRepository.findCoursesByStudentId(studentId);
    }

    public List<Cours> searchCourses(
    String title,
    String tutorName,
    Double minPrice,
    Double maxPrice,
    String category,
    Integer duration
) {
    return courseRepository.searchCourses(
        title,
        tutorName,
        minPrice,
        maxPrice,
        category,
        duration
    );
}
}