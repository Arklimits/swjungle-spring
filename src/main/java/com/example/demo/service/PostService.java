package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private List<Post> posts = new ArrayList<>();

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAllPostOrderByDateDesc();
    }

    public void addPost(Post post) {
        post.setDate();
        postRepository.save(post);
    }

    public Post getPostById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void updatePost(Post post) {
        post.setDate();
        postRepository.save(post);
    }

    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }
}