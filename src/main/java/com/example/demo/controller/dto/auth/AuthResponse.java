package com.example.demo.controller.dto.auth;

import java.util.Date;

public record AuthResponse(String jwt, String username, String expiresAt) {

}