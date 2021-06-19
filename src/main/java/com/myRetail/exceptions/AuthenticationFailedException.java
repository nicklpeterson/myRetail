package com.myRetail.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "failed to authenticate user")
public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(Throwable e) {
        super(e);
    }
}
