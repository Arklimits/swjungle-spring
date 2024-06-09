package com.example.demo.controller;

import com.example.demo.controller.dto.user.UserRequestDTO;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        if (userService.findByUsername(userRequestDTO.getUsername()) != null) {

            return new ResponseEntity<>("exist username", HttpStatus.NOT_ACCEPTABLE);
        }

        userService.saveUser(userRequestDTO);
        return new ResponseEntity<>("exist username", HttpStatus.OK);
    }
}
