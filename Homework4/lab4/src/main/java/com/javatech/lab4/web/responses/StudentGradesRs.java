package com.javatech.lab4.web.responses;

import java.util.List;

public record StudentGradesRs(
        List<StudentGrade> grades
) {
    public record StudentGrade(
            String studentCode,
            String courseCode,
            Double grade
    ){}
}
