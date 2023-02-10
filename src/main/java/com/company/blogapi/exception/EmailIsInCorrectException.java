package com.company.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Email is incorrect!")
public class EmailIsInCorrectException extends RuntimeException {
    public EmailIsInCorrectException() {
        super("Email is incorrect!");
    }
}
