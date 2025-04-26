package com.nour.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nour.demo.model.LiveSession;

@Repository
public interface LiveSessionRepository extends JpaRepository<LiveSession, Long> {

  
    List<LiveSession> findByTutorId(Long tutorId);
    List<LiveSession> findByCourseId(Long courseId);
}
