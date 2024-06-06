package com.example.demo.service;

import com.example.demo.controller.dto.PostRequestDTO;
import com.example.demo.controller.dto.PostResponseDTO;
import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class PostService {
    private final PostRepository postRepository;

    private PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllPostOrderByDateDesc();
    }

    public PostResponseDTO addPost(PostRequestDTO postRequestDTO) {
        String date = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Post post = new Post(postRequestDTO.getTitle(), postRequestDTO.getContent(), CustomUserService.getCurrentUsername(), date);

        return new PostResponseDTO(postRepository.save(post));
    }

    public PostResponseDTO getPostById(long id) {
        return new PostResponseDTO(Objects.requireNonNull(postRepository.findById(id).orElse(null)));
    }

    public PostResponseDTO updatePost(long id, PostRequestDTO postRequestDTO) {
        String date = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Post post = postRepository.findById(id).orElse(null);
        Objects.requireNonNull(post).editPost(postRequestDTO.getTitle(), postRequestDTO.getContent(), date);

        return new PostResponseDTO(postRepository.save(post));
    }

    public void deletePostById(long id) {

        postRepository.deleteById(id);
    }
}