package com.example.demo.controller;

import com.example.demo.controller.dto.UserRequestDTO;
import com.example.demo.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private CustomUserService customUserService;

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO, BindingResult bindingResult) {
        if (customUserService.findByUsername(userRequestDTO.getUsername()).isPresent()) {
            return "exist username";
        }

        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldError().getDefaultMessage();
        }

        customUserService.saveUser(userRequestDTO);
        return "register complete";
    }

//    @PostMapping("/login")
//    public UserDetails login(@Valid @RequestBody UserRequestDTO userRequestDTO) {
//        return userService.loadUserByUsername(userRequestDTO.getUsername());
//    }
}
