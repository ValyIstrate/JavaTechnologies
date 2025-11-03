package com.javatech.lab4.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "students")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "year", nullable = false)
    private int year;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StudentCourse> courses = new ArrayList<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StudentCoursePreference> coursePreferences = new ArrayList<>();

    public void setCoursePreferences(List<StudentCoursePreference> preferences) {
        this.coursePreferences.clear();
        this.coursePreferences.addAll(preferences);
    }
}
