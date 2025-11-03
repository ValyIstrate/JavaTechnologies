package com.javatech.lab4.services.dtos;

import com.javatech.lab4.persistence.entities.StudentCoursePreference;

public record CoursePreferenceDto(
        Long id,
        Long courseId,
        String code,
        String abbr,
        String name,
        Long packId,
        int rank
) {
    public static CoursePreferenceDto fromEntity(StudentCoursePreference studentCoursePreference) {
        return new CoursePreferenceDto(
                studentCoursePreference.getId(),
                studentCoursePreference.getCourse().getId(),
                studentCoursePreference.getCourse().getCode(),
                studentCoursePreference.getCourse().getAbbr(),
                studentCoursePreference.getCourse().getName(),
                studentCoursePreference.getCourse().getPack().getId(),
                studentCoursePreference.getRank()
        );
    }
}
