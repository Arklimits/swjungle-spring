package com.example.demo.controller;

import com.example.demo.controller.dto.UserRequestDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO, BindingResult bindingResult) {
        if (userService.findByUsername(userRequestDTO.getUsername()).isPresent()) {
            return "exist username";
        }

        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldError().getDefaultMessage();
        }

        userService.saveUser(userRequestDTO);
        return "register complete";
    }
}