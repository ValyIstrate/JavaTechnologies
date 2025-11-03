package com.javatech.lab4.web.requests;

public record CreateStudentRequest(
        String name,
        String email,
        String code,
        int year) {
}
