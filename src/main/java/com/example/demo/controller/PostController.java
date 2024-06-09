package com.example.demo.controller;

import com.example.demo.controller.dto.PostListDTO;
import com.example.demo.controller.dto.PostRequestDTO;
import com.example.demo.controller.dto.PostResponseDTO;
import com.example.demo.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {
    private PostService postService;

    @GetMapping("/index")
    public ResponseEntity<PostListDTO> getAllPosts(Model model) {
        PostListDTO postListDTO = new PostListDTO(postService.getAllPosts());
        return new ResponseEntity<>(postListDTO, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<PostResponseDTO> addPost(@RequestBody PostRequestDTO postRequestDTO) {
        System.out.println(postRequestDTO.getTitle() + postRequestDTO.getContent());
        PostResponseDTO postResponseDTO = postService.addPost(postRequestDTO);

        return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable("id") int id) {
        PostResponseDTO postResponseDTO = postService.getPostById(id);

        return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable("id") long id, @RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO postResponseDTO = postService.updatePost(id, postRequestDTO);

        return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Long> removePost(@PathVariable("id") long id) {
        PostResponseDTO postResponseDTO = postService.getPostById(id);

        id = postService.deletePostById(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
