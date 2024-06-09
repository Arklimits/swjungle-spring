package com.example.demo.controller;

import com.example.demo.controller.dto.comment.CommentRequestDTO;
import com.example.demo.controller.dto.comment.CommentResponseDTO;
import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post/{postId}/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable("postId") Long postId, @RequestBody CommentRequestDTO commentRequestDTO) {

        CommentResponseDTO commentResponseDTO = commentService.saveComment(postId, commentRequestDTO);

        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }

    @GetMapping
    public List<Comment> getComments(@PathVariable("postId") Long postId) {

        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> getComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {

        return new ResponseEntity<>(commentService.getCommentsById(commentId), HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO commentResponseDTO = commentService.updateComment(commentId, commentRequestDTO);

        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        CommentResponseDTO commentResponseDTO = commentService.deleteComment(commentId);

        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }
}
