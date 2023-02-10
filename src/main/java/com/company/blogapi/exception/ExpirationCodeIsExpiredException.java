package com.company.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Your Confirmed link of Expiration Time is Expired!")
public class ExpirationCodeIsExpiredException extends RuntimeException {
    public ExpirationCodeIsExpiredException() {
        super("Your Confirmed link of Expiration Time is Expired!");
    }
}
