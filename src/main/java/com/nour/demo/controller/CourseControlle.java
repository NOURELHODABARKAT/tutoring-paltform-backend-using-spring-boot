package com.nour.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nour.demo.dto.CourseDTO;
import com.nour.demo.dto.EnrollmentDTO;
import com.nour.demo.model.courese;
import com.nour.demo.repository.CourseRepository;
import com.nour.demo.service.CourseService;


@RestController
@RequestMapping("/api/courses")
public class CourseControlle {

    private final CourseRepository courseRepository;
    private final CourseService courseService;

    public CourseControlle(CourseRepository courseRepository, CourseService courseService) {
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> createCourse(@RequestBody CourseDTO courseDTO) {
        courese course = new courese();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setPrice(courseDTO.getPrice());
        courseRepository.save(course);
        return new ResponseEntity<>("Course created", HttpStatus.CREATED);
    }
    @PutMapping("/{id}/edit")
public ResponseEntity<String> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
    return courseRepository.findById(id).map(course -> {
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setPrice(courseDTO.getPrice());
        courseRepository.save(course);
        return new ResponseEntity<>("Course updated", HttpStatus.OK);
    }).orElseGet(() -> new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND));
}

@DeleteMapping("/{id}/delete")
public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
    return courseRepository.findById(id).map(course -> {
        courseRepository.delete(course);
        return new ResponseEntity<>("Course deleted", HttpStatus.OK);
    }).orElseGet(() -> new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND));
}
@PreAuthorize("hasRole('STUDENT')")
@PostMapping("/{courseId}/enroll/{studentId}")
public ResponseEntity<String> enrollStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
    EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
    enrollmentDTO.setCourseId(courseId);
    enrollmentDTO.setStudentId(studentId);
    return courseService.enrollStudent(enrollmentDTO);
}

@PreAuthorize("hasRole('STUDENT')")
@GetMapping("/my-courses/{studentId}")
public ResponseEntity<List<courese>> getMyCourses(@PathVariable Long studentId) {
    List<courese> courses = courseService.getCoursesByStudentId(studentId);
    return ResponseEntity.ok(courses);
}
@GetMapping("/search")
public ResponseEntity<List<courese>> searchCourses(
    @RequestParam(required = false) String title,
    @RequestParam(required = false) String tutorName,
    @RequestParam(required = false) Double minPrice,
    @RequestParam(required = false) Double maxPrice,
    @RequestParam(required = false) String category,
    @RequestParam(required = false) String subjectStartsWith,
    @RequestParam(required = false) Integer duration
) {
    List<courese> courses = courseService.searchCourses(
        title,
        minPrice,
        maxPrice,
        category,
        subjectStartsWith,
        duration
    );

    if (courses.isEmpty()) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(List.of());
    }

    return ResponseEntity.ok(courses);
}

    
}