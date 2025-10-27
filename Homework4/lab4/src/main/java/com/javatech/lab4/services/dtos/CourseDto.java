package com.javatech.lab4.services.dtos;

import com.javatech.lab4.persistence.entities.Course;

import java.util.Objects;

public record CourseDto(
        Long id,
        String type,
        String code,
        String abbr,
        String name,
        String instructorName,
        Long instructorId,
        Long packId,
        int groupCount,
        String description
) {
    public static CourseDto fromEntity(Course course) {
        return new CourseDto(
                course.getId(),
                course.getType(),
                course.getCode(),
                course.getAbbr(),
                course.getName(),
                course.getInstructor().getName(),
                course.getInstructor().getId(),
                Objects.nonNull(course.getPack()) ? course.getPack().getId() : null,
                course.getGroupCount(),
                course.getDescription()
        );
    }
}
