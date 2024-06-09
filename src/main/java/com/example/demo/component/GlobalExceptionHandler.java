package com.example.demo.component;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.net.URISyntaxException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e) throws URISyntaxException {
        final ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
                .type(new URI("POST"))
                .typeMessageCode("Bad Request")
                .titleMessageCode("NullPointerException")
                .detailMessageCode(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
