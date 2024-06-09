package com.example.demo.exception;

import org.springframework.security.core.AuthenticationException;

public class UsernameFoundException extends AuthenticationException {
    public UsernameFoundException(String msg) {
        super(msg);
    }

    public UsernameFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
