package com.example.demo.service;

import com.example.demo.controller.dto.PostRequestDTO;
import com.example.demo.controller.dto.PostResponseDTO;
import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    /** 전체 Post 로드
     *
     * @return 수정된 날짜 기준으로 내림차순 정렬된 Posts 리스트
     */
    public List<Post> getAllPosts() {

        return postRepository.findAllPostOrderByDateDesc();
    }

    /** Post 게시
     *
     * @param postRequestDTO Post Request DTO
     * @return PostResponseDTO
     */
    public PostResponseDTO addPost(PostRequestDTO postRequestDTO) {
        String date = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Post post = new Post(postRequestDTO.getTitle(), postRequestDTO.getContent(), UserService.getCurrentUsername(), date);

        return new PostResponseDTO(postRepository.save(post));
    }

    /** Post 찾기
     *
     * @param id Post ID
     * @return PostResponseDTO
     */
    public PostResponseDTO getPostById(long id) {

        return new PostResponseDTO(postRepository.findById(id).orElseThrow(()-> new RuntimeException("Post not found")));
    }

    /** Post 업데이트
     *
     * @param id Post ID
     * @param postRequestDTO Original PostResponseDTO
     * @return Updated PostResponseDTO
     */
    public PostResponseDTO updatePost(long id, PostRequestDTO postRequestDTO) {
        String date = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Post post = postRepository.findById(id).orElse(null);
        Objects.requireNonNull(post).editPost(postRequestDTO.getTitle(), postRequestDTO.getContent(), date);

        return new PostResponseDTO(postRepository.save(post));
    }

    /** Post 삭제
     *
     * @param id Post ID
     */
    public long deletePostById(long id) {

        postRepository.deleteById(id);
        return id;
    }
}