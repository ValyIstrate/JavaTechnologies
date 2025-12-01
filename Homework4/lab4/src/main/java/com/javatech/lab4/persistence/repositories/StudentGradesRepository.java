package com.javatech.lab4.persistence.repositories;

import com.javatech.lab4.persistence.entities.StudentGrades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentGradesRepository extends JpaRepository<StudentGrades, Long> {

    @Query("""
        select sg
        from StudentGrades sg
        where
            (:studentCode is null or sg.student.code = :studentCode)
            and
            (:courseCode is null or sg.course.code = :courseCode)
    """)
    List<StudentGrades> findAllByStudentCodeAndCourseCode(
            @Param("studentCode") String studentCode,
            @Param("courseCode") String courseCode
    );
}
