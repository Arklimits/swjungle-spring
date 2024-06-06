package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
public class PostDTO {
    @NotNull
    private String title;
    @NotNull
    private String content;
}
