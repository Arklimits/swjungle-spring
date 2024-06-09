package com.example.demo.exception;

public class ExpiredJwtTokenException extends RuntimeException {
    public ExpiredJwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}