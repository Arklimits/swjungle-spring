package com.example.demo.service;

import com.example.demo.controller.dto.CommentRequestDTO;
import com.example.demo.controller.dto.CommentResponseDTO;
import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;

    /**
     * Comment 저장
     *
     * @param postId            Comment ID
     * @param commentRequestDTO Comment
     * @return 저장된 Comment
     */
    public CommentResponseDTO saveComment(Long postId, CommentRequestDTO commentRequestDTO) {
        String date = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Comment comment = new Comment(commentRequestDTO.getContent(), UserService.getCurrentUsername(), date, postId);

        return new CommentResponseDTO(commentRepository.save(comment));
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
