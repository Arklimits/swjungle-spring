package com.example.demo.controller;

import com.example.demo.model.Post;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/post/new/")
    public String getNewPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "postForm";
    }

    @PostMapping("/post")
    public String addPost(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        postService.addPost(post);
        redirectAttributes.addAttribute("id", post.getId());
        return "redirect:/post/{id}";
    }

    @GetMapping("/post/{id}")
    public String getPostById(@PathVariable int id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "postDetail";
    }

    @GetMapping("/post/edit/{id}")
    public String getEditPostForm(@PathVariable int id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "editPost";
    }

    @PostMapping("/post/edit/{id}")
    public String updatePost(@ModelAttribute Post post) {
        postService.updatePost(post);
        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/post/del/{id}")
    public String removePost(@PathVariable int id) {
        postService.deletePostById(id);
        return "redirect:/";
    }
}
