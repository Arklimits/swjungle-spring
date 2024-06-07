package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String modifiedDate;
    private String createdDate;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public void editComment(String content, String author, String modifiedDate) {
        this.content = content;
        this.author = author;
        this.modifiedDate = modifiedDate;
    }
}
