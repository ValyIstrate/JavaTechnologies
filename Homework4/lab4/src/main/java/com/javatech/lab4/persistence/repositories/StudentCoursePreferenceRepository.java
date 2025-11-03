package com.javatech.lab4.persistence.repositories;

import com.javatech.lab4.persistence.entities.StudentCoursePreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface StudentCoursePreferenceRepository extends JpaRepository<StudentCoursePreference, Integer> {

    @Query("""
        select s from StudentCoursePreference s
        where s.student.id = :id and s.course.id in :courseIds
    """)
    List<StudentCoursePreference> findByStudentIdAndCourseIdIn(Long id, List<Long> courseIds);
}
