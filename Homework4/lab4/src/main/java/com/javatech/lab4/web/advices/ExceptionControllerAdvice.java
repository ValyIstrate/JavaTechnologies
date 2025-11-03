package com.javatech.lab4.web.advices;

import com.javatech.lab4.services.exceptions.EntityNotFoundException;
import com.javatech.lab4.services.exceptions.StudentAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ProblemDetail handleUnauthorizedErrors(StudentAlreadyExistsException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleUnauthorizedErrors(EntityNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

}
