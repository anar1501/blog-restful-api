package com.company.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Password is wrong!")
public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Password is wrong!");
    }
}