package com.company.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User already confirmed!")
public class AlreadyConfirmedException extends RuntimeException {
    public AlreadyConfirmedException() {
        super("User already confirmed!");
    }
}
