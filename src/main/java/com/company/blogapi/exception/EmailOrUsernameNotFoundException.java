package com.company.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Email or Username Not Found!")
public class EmailOrUsernameNotFoundException extends RuntimeException {
    public EmailOrUsernameNotFoundException() {
        super("Email or Username Not Found!");
    }
}
