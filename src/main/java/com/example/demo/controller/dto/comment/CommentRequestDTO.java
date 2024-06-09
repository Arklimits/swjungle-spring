package com.example.demo.controller.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentRequestDTO {
    @NotNull
    private String content;
}
