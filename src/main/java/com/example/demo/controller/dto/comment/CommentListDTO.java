package com.example.demo.controller.dto.comment;

import com.example.demo.model.Comment;

import java.util.List;

public record CommentListDTO(List<Comment> comments) {
}
