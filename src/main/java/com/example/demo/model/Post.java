package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.format.DateTimeFormatter;


@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String content;
    private String author;
    private String date;

    public Post() {
    }

    public Post(String title, String content, String author, String date) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
    }

    // Getter and Setter for id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for content
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Getter and Setter for author
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Getter and Setter for date
    public String getDate() {
        return date;
    }

    public void setDate() {
        this.date = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
