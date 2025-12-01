package com.javatech.lab4.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Getter
@Setter
@Table(name = "student_grades")
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentGrades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "grade", nullable = false)
    private Double grade;

    public StudentGrades(Student student, Course presentCourse, Double grade) {
        this.student = student;
        this.course = presentCourse;
        this.grade = grade;
    }
}
