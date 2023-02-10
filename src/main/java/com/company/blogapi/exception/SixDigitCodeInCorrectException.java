package com.company.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "6 digit code is wrong!")
public class SixDigitCodeInCorrectException extends RuntimeException {
    public SixDigitCodeInCorrectException() {
        super("6 digit code is wrong!");
    }
}
