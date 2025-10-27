package com.javatech.lab4.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_course",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "pack_id"}))
@Getter
@Setter
@NoArgsConstructor
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pack_id")
    private Pack pack;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;
}
