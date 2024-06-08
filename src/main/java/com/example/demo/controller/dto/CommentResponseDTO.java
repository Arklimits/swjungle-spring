package com.example.demo.controller.dto;

import com.example.demo.model.Comment;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public record CommentResponseDTO(Comment comment) {

}
