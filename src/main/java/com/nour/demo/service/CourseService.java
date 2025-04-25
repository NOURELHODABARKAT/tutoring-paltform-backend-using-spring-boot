package com.nour.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.nour.demo.dto.CourseDTO;
import com.nour.demo.dto.EnrollmentDTO;
import com.nour.demo.model.User;
import com.nour.demo.model.courese;
import com.nour.demo.repository.CourseRepository;
import com.nour.demo.repository.UserRepository;

@Service
public class CourseService {

    private static final String COURSE_NOT_FOUND = "Course not found";

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public courese createCourse(CourseDTO dto, User currentUser) {
       
        if (currentUser.getRoles().stream().noneMatch(role -> "TUTOR".equals(role.toString()))) {
            throw new AccessDeniedException("Only tutors can publish courses");
        }

        courese course = new courese(); // Preserved typo in class name
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
        Optional<courese> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        courese course = optionalCourse.get();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());
        courseRepository.save(course);

        return new ResponseEntity<>("Course updated", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteCourse(Long id) {
        Optional<courese> optionalCourse = courseRepository.findById(id); // Preserved typo
        if (optionalCourse.isEmpty()) {
            return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        courseRepository.delete(optionalCourse.get());
        return new ResponseEntity<>("Course deleted", HttpStatus.OK);
    }
    public ResponseEntity<String> publishCourse(Long id) {
        Optional<courese> optionalCourse = courseRepository.findById(id); // Preserved typo
        if (optionalCourse.isEmpty()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        courese course = optionalCourse.get();
        course.setStatus("published");
        courseRepository.save(course);

        return new ResponseEntity<>("Course published", HttpStatus.OK);
    }
    public ResponseEntity<String> enrollStudent(EnrollmentDTO dto) {
        Optional<courese> courseOpt = courseRepository.findById(dto.getCourseId());
        Optional<User> studentOpt = userRepository.findById(dto.getStudentId());
    
        if (courseOpt.isEmpty() || studentOpt.isEmpty()) {
            return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    
        courese course = courseOpt.get();
        User student = studentOpt.get();
    
        if (course.getEnrolledStudents().contains(student)) {
            return new ResponseEntity<>("Student already enrolled", HttpStatus.BAD_REQUEST);
        }
    
        course.getEnrolledStudents().add(student);
        courseRepository.save(course);
    
        return new ResponseEntity<>("Student enrolled successfully", HttpStatus.OK);
    }
    public List<courese> getCoursesByStudentId(Long studentId) {
     
        return courseRepository.findCoursesByStudentId(studentId);
    }
    public List<courese>  searchCourses(
        String tutorName,
        Double minPrice,
        Double maxPrice,
        String category,
        String subjectStartsWith,
        Integer duration
    ) {
        if (tutorName == null) {
            tutorName = "";
        }
        return courseRepository.searchCourses(tutorName, minPrice, maxPrice, category, subjectStartsWith, duration);
    }
}