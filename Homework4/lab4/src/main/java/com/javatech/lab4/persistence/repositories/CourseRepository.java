package com.javatech.lab4.persistence.repositories;

import com.javatech.lab4.persistence.entities.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    // Already exists
    // Optional<Course> findById(Long id);

    Optional<Course> findByName(String courseName);

    @Query("""
        SELECT c FROM Course c
        WHERE c.instructor.id = :instructorId
    """)
    List<Course> findCoursesByInstructorId(Long instructorId);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Course c
        SET c.type = :type
        WHERE c.code = :code
    """)
    void updateCourseType(Integer type, String code);
}
