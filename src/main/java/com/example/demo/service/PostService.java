package com.example.demo.service;

import com.example.demo.controller.dto.PostDTO;
import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAllPostOrderByDateDesc();
    }

    public long addPost(PostDTO postDTO) {
        String date = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Post post = new Post(postDTO.getTitle(), postDTO.getContent(), UserService.getCurrentUsername(), date);
        Post save = postRepository.save(post);

        return save.getId();
    }

    public Post getPostById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public long updatePost(long id, PostDTO postDTO) {
        String date = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Post post = postRepository.findById(id).orElse(null);
        post.editPost(postDTO.getTitle(), postDTO.getContent(), date);
        Post save = postRepository.save(post);

        return save.getId();
    }

    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }
}