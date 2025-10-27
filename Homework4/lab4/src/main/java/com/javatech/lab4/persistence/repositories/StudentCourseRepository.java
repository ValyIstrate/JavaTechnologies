package com.javatech.lab4.persistence.repositories;

import com.javatech.lab4.persistence.entities.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
}
