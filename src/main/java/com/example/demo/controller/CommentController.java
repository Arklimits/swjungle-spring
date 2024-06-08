package com.example.demo.controller;

import com.example.demo.controller.dto.CommentRequestDTO;
import com.example.demo.controller.dto.CommentResponseDTO;
import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post/{postId}/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @PostMapping("/new")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable("postId") Long postId, @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO commentResponseDTO = commentService.saveComment(postId, commentRequestDTO);

        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }

    @GetMapping
    public List<Comment> getComments(@PathVariable("postId") Long postId) {

        return commentService.getCommentsByPostId(postId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }
}
