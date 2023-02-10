package com.company.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Such username already exists!")
public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException(){
        super("Such username already exists!");
    }
}
