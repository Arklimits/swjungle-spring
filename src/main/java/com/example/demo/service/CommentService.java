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

    /**
     * Comment 저장
     *
     * @param postId  Post ID
     * @param comment Comment
     * @return 저장된 Comment
     */
    public Comment saveComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post);
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
     * @param id
     */
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
