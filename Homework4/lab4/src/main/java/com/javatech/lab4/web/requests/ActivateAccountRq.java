package com.javatech.lab4.web.requests;

public record ActivateAccountRq(
        String email,
        String password,
        String otp
) {
}
