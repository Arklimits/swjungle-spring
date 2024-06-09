package com.example.demo.component;

import org.springframework.security.core.AuthenticationException;

public class UsernameFoundException extends AuthenticationException {
    public UsernameFoundException(String msg) {
        super(msg);
    }

    public UsernameFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
