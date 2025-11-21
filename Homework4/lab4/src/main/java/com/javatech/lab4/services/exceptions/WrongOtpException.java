package com.javatech.lab4.services.exceptions;

public class WrongOtpException extends RuntimeException {
    public WrongOtpException() {
        super("Your code is wrong!");
    }
}
