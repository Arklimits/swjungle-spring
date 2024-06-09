package com.example.demo.component;

import com.example.demo.exception.ExpiredJwtTokenException;
import com.example.demo.exception.InvalidJwtTokenException;
import com.example.demo.exception.UsernameFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
                .typeMessageCode("Error Message")
                .titleMessageCode("Bad Request")
                .detailMessageCode("NullPointerException")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
                .typeMessageCode("Error Message")
                .titleMessageCode("Bad Request")
                .detailMessageCode("UsernameNotFoundException")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUsernameFoundException(UsernameFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
                .typeMessageCode("Error Message")
                .titleMessageCode("Bad Request")
                .detailMessageCode("UsernameFoundException")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(AccessDeniedException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.FORBIDDEN, e.getMessage())
                .typeMessageCode("Error Message")
                .titleMessageCode("Access Denied")
                .detailMessageCode("AccessDeniedException")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtTokenException.class)
    protected ResponseEntity<ErrorResponse> handleExpiredJwtTokenException(ExpiredJwtTokenException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
                .typeMessageCode("Error Message")
                .titleMessageCode("Expired JWT")
                .detailMessageCode("ExpiredJwtException")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidJwtTokenException(InvalidJwtTokenException e) {
        final ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
                .typeMessageCode("Error Message")
                .titleMessageCode(e.getMessage())
                .detailMessageCode("InvalidJwtTokenException")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
