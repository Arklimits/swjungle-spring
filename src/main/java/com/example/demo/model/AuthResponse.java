package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AuthResponse {
    private final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }
}