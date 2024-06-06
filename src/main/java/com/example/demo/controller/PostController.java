package com.example.demo.controller;

import com.example.demo.controller.dto.PostListDTO;
import com.example.demo.controller.dto.PostRequestDTO;
import com.example.demo.controller.dto.PostResponseDTO;
import com.example.demo.service.PostService;
import com.example.demo.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public ResponseEntity<PostListDTO> getAllPosts(Model model) {
        PostListDTO postListDTO = new PostListDTO(postService.getAllPosts());
        return new ResponseEntity<>(postListDTO, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<PostResponseDTO> addPost(@RequestBody PostRequestDTO postRequestDTO) {
        System.out.println(postRequestDTO.getTitle() + postRequestDTO.getContent());
        PostResponseDTO postResponseDTO = postService.addPost(postRequestDTO);

        return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable("id") int id) {
        PostResponseDTO postResponseDTO = postService.getPostById(id);

        return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/post/edit/{id}")
    public ResponseEntity<PostResponseDTO> getEditPostForm(@PathVariable long id) {
        PostResponseDTO postResponseDTO = postService.getPostById(id);
        if (CustomUserService.getCurrentUserRole().equals("[ROLE_ADMIN]")) {

            return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(postResponseDTO, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping("/post/edit/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable("id") long id, @RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO postResponseDTO = postService.updatePost(id, postRequestDTO);

        return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/post/del/{id}")
    public ResponseEntity<?> removePost(@PathVariable("id") long id) {
        PostResponseDTO postResponseDTO = postService.getPostById(id);
        System.out.println(CustomUserService.getCurrentUserRole());
        if (CustomUserService.getCurrentUserRole().equals("[ROLE_ADMIN]")) {
            postService.deletePostById(id);

            return new ResponseEntity<Long>(id, HttpStatus.OK);
        }

        return new ResponseEntity<PostResponseDTO>(postResponseDTO, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
