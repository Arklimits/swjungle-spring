package com.example.demo.controller;

import com.example.demo.controller.dto.UserRequestDTO;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        if (userService.findByUsername(userRequestDTO.getUsername()) != null) {
            return "exist username";
        }

        userService.saveUser(userRequestDTO);
        return "register complete";
    }
}
