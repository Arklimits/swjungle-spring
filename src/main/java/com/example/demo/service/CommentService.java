package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public Comment saveComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostIdCommentOrderedByDateDesc(postId);
    }

    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }
}
