package com.javatech.lab4.services.dtos;

import com.javatech.lab4.persistence.entities.Student;

import java.util.List;

public record StudentDto(
        Long id,
        String name,
        String email,
        String code,
        int year,
        List<CourseDto> courses,
        List<CoursePreferenceDto> coursePreferences
) {
    public static StudentDto fromEntity(Student student) {
        return new StudentDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCode(),
                student.getYear(),
                student.getCourses().stream()
                        .map(studentCourse -> CourseDto.fromEntity(studentCourse.getCourse()))
                        .toList(),
                student.getCoursePreferences().stream()
                        .map(CoursePreferenceDto::fromEntity)
                        .toList()
        );
    }
}
