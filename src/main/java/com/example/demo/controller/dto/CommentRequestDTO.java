package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentRequestDTO {
    @NotNull
    private String content;
}
