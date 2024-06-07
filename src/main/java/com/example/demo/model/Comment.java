package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    @Setter
    private Post post;

    public void editComment(String content, String author, String modifiedAt) {
        this.content = content;
        this.author = author;
        this.modifiedAt = modifiedAt;
    }
}
