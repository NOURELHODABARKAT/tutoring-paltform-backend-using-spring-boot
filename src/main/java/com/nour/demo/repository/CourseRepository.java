package com.nour.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nour.demo.model.User;
import com.nour.demo.model.courese;
@Repository
public interface CourseRepository extends JpaRepository<courese, Integer> {
    List<courese> findByTutor(User tutor);
    List<courese> findByStatus(String status);
    List<courese> findByTitleContainingIgnoreCase(String keyword);
    Optional<courese> findById(Long id);
     @Query("SELECT c FROM courese c JOIN c.enrolledStudents s WHERE s.id = :studentId")
    List<courese> findCoursesByStudentId(@Param("studentId") Long studentId);

@Query("SELECT c FROM courese c JOIN c.tutor t WHERE t.id = :tutorId")
    List<courese> findCoursesByTutorId(@Param("tutorId") Long tutorId);
    @Query("SELECT c FROM courese c WHERE c.status = 'published'")
    List<courese> findAllPublishedCourses();


    @Query("SELECT c FROM courese c WHERE c.status = 'draft'")
    List<courese> findAllDraftCourses();
    @Query("SELECT c FROM courese c WHERE c.status = 'archived'")
    List<courese> findAllArchivedCourses();
    @Query("SELECT c FROM courese c WHERE c.status = 'deleted'")
    List<courese> findAllDeletedCourses();
    @Query("SELECT c FROM courese c WHERE c.status = 'active'")
    List<courese> findAllActiveCourses();
    @Query("SELECT c FROM courese c WHERE c.status = 'inactive'")
    List<courese> findAllInactiveCourses();
    @Query("SELECT c FROM courese c WHERE " +
    "(:title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
    "(LOWER(c.tutor.username) LIKE LOWER(CONCAT('%', :tutorName, '%'))) AND " +
    "(:minPrice IS NULL OR c.price >= :minPrice) AND " +
    "(:maxPrice IS NULL OR c.price <= :maxPrice) AND " +
    "(:category IS NULL OR LOWER(c.category) LIKE LOWER(CONCAT('%', :category, '%'))) AND " +
    "(:subjectStartsWith IS NULL OR LOWER(c.subject) LIKE LOWER(CONCAT(:subjectStartsWith, '%'))) AND " +
    "(:duration IS NULL OR c.duration = :duration)")
List<courese> searchCourses(
 @Param("title") String title,
 @Param("tutorName") String tutorName,
 @Param("minPrice") Double minPrice,
 @Param("maxPrice") Double maxPrice,
 @Param("category") String category,
 @Param("subjectStartsWith") String subjectStartsWith,
 @Param("duration") Integer duration
);
}