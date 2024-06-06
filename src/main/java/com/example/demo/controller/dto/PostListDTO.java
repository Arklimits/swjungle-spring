package com.example.demo.controller.dto;

import com.example.demo.model.Post;

import java.util.List;

public record PostListDTO(List<Post> posts) {
}
