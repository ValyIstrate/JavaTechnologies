package com.jt.quick_grade.web.requests;

public record PostGradeRq(
        String studentCode,
        String courseCode,
        Double grade
) {
}
