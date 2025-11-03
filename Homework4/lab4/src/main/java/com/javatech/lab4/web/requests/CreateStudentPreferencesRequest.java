package com.javatech.lab4.web.requests;

import java.util.List;

public record CreateStudentPreferencesRequest(
        List<CreateStudentPackPreferencesDto> packs
) {
}
