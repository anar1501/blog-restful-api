package com.company.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Password repeat is not same!")
public class PasswordRepeatNotSameException extends RuntimeException {
    public PasswordRepeatNotSameException(){
        super("Password repeat is not same!");
    }
}