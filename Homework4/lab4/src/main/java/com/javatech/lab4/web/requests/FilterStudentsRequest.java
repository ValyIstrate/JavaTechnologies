package com.javatech.lab4.web.requests;

public record FilterStudentsRequest(
        Long year,
        String search
) {
}
