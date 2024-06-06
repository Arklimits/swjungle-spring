package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
public class PostRequestDTO {
    @NotNull
    private String title;
    @NotNull
    private String content;
}
