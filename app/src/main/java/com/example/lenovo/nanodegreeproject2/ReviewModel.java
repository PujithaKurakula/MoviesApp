package com.example.lenovo.nanodegreeproject2;

public class ReviewModel {

    String author,content;
    public ReviewModel(String reviewauthor, String reviewcontent) {
        this.author=reviewauthor;
        this.content=reviewcontent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
