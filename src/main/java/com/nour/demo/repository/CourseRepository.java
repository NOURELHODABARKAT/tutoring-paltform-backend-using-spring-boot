package com.nour.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nour.demo.model.Cours;
import com.nour.demo.model.User.User;

@Repository
public interface CourseRepository extends JpaRepository<Cours, Long> {
    List<Cours> findByTutor(User tutor);

    List<Cours> findByStatus(String status);

    List<Cours> findByTitleContainingIgnoreCase(String keyword);

    Optional<Cours> findById(Long id);

    @Query("SELECT c FROM Cours c JOIN c.enrolledStudents s WHERE s.id = :studentId")
    List<Cours> findCoursesByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT c FROM Cours c JOIN c.tutor t WHERE t.id = :tutorId")
    List<Cours> findCoursesByTutorId(@Param("tutorId") Long tutorId);

    @Query("SELECT c FROM Cours c WHERE c.status = 'published'")
    List<Cours> findAllPublishedCourses();

    @Query("SELECT c FROM Cours c WHERE c.status = 'draft'")
    List<Cours> findAllDraftCourses();

    @Query("SELECT c FROM Cours c WHERE c.status = 'archived'")
    List<Cours> findAllArchivedCourses();

    @Query("SELECT c FROM Cours c WHERE c.status = 'deleted'")
    List<Cours> findAllDeletedCourses();

    @Query("SELECT c FROM Cours c WHERE c.status = 'active'")
    List<Cours> findAllActiveCourses();

    @Query("SELECT c FROM Cours c WHERE c.status = 'inactive'")
    List<Cours> findAllInactiveCourses();
long countByTutorId(Long tutorId);
    @Query("SELECT c FROM Cours c WHERE " +
            "(:title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:tutorName IS NULL OR LOWER(c.tutor.firstName) LIKE LOWER(CONCAT('%', :tutorName, '%')) OR LOWER(c.tutor.lastName) LIKE LOWER(CONCAT('%', :tutorName, '%'))) AND "
            +
            "(:minPrice IS NULL OR c.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR c.price <= :maxPrice) AND " +
            "(:category IS NULL OR LOWER(c.category) LIKE LOWER(CONCAT('%', :category, '%'))) AND " +
            "(:duration IS NULL OR c.duration = :duration)")
    List<Cours> searchCourses(
            @Param("title") String title,
            @Param("tutorName") String tutorName,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("category") String category,
            @Param("subjectStartsWith") String subjectStartsWith, // This parameter is kept but not used
            @Param("duration") Integer duration);
}