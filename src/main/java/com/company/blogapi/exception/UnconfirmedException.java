package com.company.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Account is not confirmed!")
public class UnconfirmedException extends RuntimeException {
    public UnconfirmedException() {
        super("Account is not confirmed!");
    }
}
