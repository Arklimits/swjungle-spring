package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String content;
    private String author;
    private String createdAt;
    private String modifiedAt;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(String content, String author, String date) {
        this.content = content;
        this.author = author;
        this.createdAt = date;
        this.modifiedAt = date;
    }

    public void editComment(String content, String modifiedAt) {
        this.content = content;
        this.modifiedAt = modifiedAt;
    }
}
