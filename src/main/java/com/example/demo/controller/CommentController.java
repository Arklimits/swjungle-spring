package com.example.demo.controller;

import com.example.demo.controller.dto.CommentRequestDTO;
import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post/{postId}/comment")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;

    @PostMapping
    public Comment createComment(@PathVariable("postId") Long postId, @RequestBody CommentRequestDTO commentRequestDTO) {

        return commentService.saveComment(postId, commentRequestDTO);
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
