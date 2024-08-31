package com.tediproject.tedi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;

    @Lob 
    @Column(length = 16777216)
    private String comment;

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poster") 
    private UserEntity poster;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article") 
    private Article article;

    


    public Comments() {
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public UserEntity getPoster() {
        return poster;
    }


    public void setPoster(UserEntity poster) {
        this.poster = poster;
    }


    public Article getArticle() {
        return article;
    }


    public void setArticle(Article article) {
        this.article = article;
    }
}
