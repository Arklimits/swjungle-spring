package com.example.demo.service;

import com.example.demo.controller.dto.CommentRequestDTO;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    /**
     * Comment 저장
     *
     * @param postId  Comment ID
     * @param commentRequestDTO Comment
     * @return 저장된 Comment
     */
    public Comment saveComment(Long postId, CommentRequestDTO commentRequestDTO) {
        String date = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = new Comment(commentRequestDTO.getContent(), UserService.getCurrentUsername(), date, post);

        return commentRepository.save(comment);
    }

    /**
     * PostId 에 맞는 Comment 찾기
     *
     * @param postId Post ID
     * @return 생성날짜 기준으로 내림차순된 Comments
     */
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostIdCommentOrderedByDateDesc(postId);
    }

    /**
     * Comment 삭제
     *
     * @param id Comment ID
     */
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
