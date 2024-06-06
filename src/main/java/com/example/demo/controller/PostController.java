package com.example.demo.controller;

import com.example.demo.controller.dto.PostDTO;
import com.example.demo.model.Post;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Long> addPost(@RequestBody PostDTO postDTO) {
        long id = postService.addPost(postDTO);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") int id) {
        Post post = postService.getPostById(id);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/post/edit/{id}")
    public ResponseEntity<Post> getEditPostForm(@PathVariable long id) {
        Post post = postService.getPostById(id);
        if (UserService.getCurrentUserRole().equals("[ROLE_ADMIN]")) {

            return new ResponseEntity<>(post, HttpStatus.OK);
        }

        return new ResponseEntity<>(post, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PostMapping("/post/edit/{id}")
    public ResponseEntity<Long> updatePost(@PathVariable("id") long id, @RequestBody PostDTO postDTO) {
        postService.updatePost(id, postDTO);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/post/del/{id}")
    public String removePost(@PathVariable int id) {
        Post post = postService.getPostById(id);
        System.out.println(UserService.getCurrentUserRole());
        if (UserService.getCurrentUserRole().equals("[ROLE_ADMIN]")) {
            postService.deletePostById(id);
            return "redirect:/";
        }

        return "redirect:/post/" + id;
    }
}
