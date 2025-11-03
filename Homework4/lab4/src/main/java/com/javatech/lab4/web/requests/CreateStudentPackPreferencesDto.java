package com.javatech.lab4.web.requests;

import java.util.List;

public record CreateStudentPackPreferencesDto(
        Long packId,
        List<Long> courses
) {
}
