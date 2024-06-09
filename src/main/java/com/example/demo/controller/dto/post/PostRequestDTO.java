package com.example.demo.controller.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
public class PostRequestDTO {
    @NotNull
    private String title;
    @NotNull
    private String content;
}
