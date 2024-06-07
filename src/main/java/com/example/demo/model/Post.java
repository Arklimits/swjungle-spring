package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.Getter;


@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String content;
    private String author;
    private String createdAt;
    private String modifiedAt;

    public Post(String title, String content, String author, String date) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = date;
        this.modifiedAt = date;
    }

    public void editPost(String title, String content, String date){
        this.title = title;
        this.content = content;
        this.modifiedAt = date;
    }
}
