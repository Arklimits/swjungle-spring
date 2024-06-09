package com.example.demo.service;

import com.example.demo.controller.dto.comment.CommentRequestDTO;
import com.example.demo.controller.dto.comment.CommentResponseDTO;
import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    /**
     * Comment 저장
     *
     * @param postId            Comment ID
     * @param commentRequestDTO Comment
     * @return 저장된 Comment
     */
    public CommentResponseDTO saveComment(Long postId, CommentRequestDTO commentRequestDTO) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (postRepository.findById(postId).isEmpty()) {
            throw new NullPointerException("Post does not exist");
        }
        Comment comment = new Comment(commentRequestDTO.getContent(), userService.getCurrentUsername(), date, postId);

        return new CommentResponseDTO(commentRepository.save(comment));
    }

    /**
     * Comment 업데이트
     *
     * @param id                Comment ID
     * @param commentRequestDTO Original CommentResponseDTO
     * @return Updated CommentResponseDTO
     */
    public CommentResponseDTO updateComment(long id, CommentRequestDTO commentRequestDTO) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NullPointerException("Comment not found"));

        Done:
        {
            if (userService.checkAdministrator())
                break Done;

            if (!userService.checkAuthor(comment.getAuthor()))
                throw new AccessDeniedException("You are not Author");
        }

        comment.editComment(commentRequestDTO.getContent(), date);

        return new CommentResponseDTO(commentRepository.save(comment));
    }

    /**
     * Comment ID 찾기
     *
     * @param commentId Comment ID
     * @return CommentResponseDTO
     */
    public CommentResponseDTO getCommentsById(Long commentId) {

        return new CommentResponseDTO(commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("Comment not found")));
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
    public CommentResponseDTO deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NullPointerException("Comment not found"));

        Done:
        {
            if (userService.checkAdministrator())
                break Done;

            if (!userService.checkAuthor(comment.getAuthor()))
                throw new AccessDeniedException("You are not Author");
        }

        commentRepository.deleteById(id);

        return new CommentResponseDTO(comment);
    }
}
