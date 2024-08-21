package com.tediproject.tedi.model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime; 
import java.util.List;

@Entity
public class Article {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;

    @Column 
    private String title;

    @Column
    private String content;

    @Lob
    @Column(length=100000)
    private byte[] picture;

    @Lob
    @Column(length=100000)
    private byte[] video;

    


    @Column(name = "date_posted")
    private LocalDateTime date_posted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author") 
    private UserEntity author;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    List <Likes> likes;

    public Article() {}


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public byte[] getPicture() {
        return picture;
    }


    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public byte[] getVideo() {
        return video;
    }


    public void setVideo(byte[] video) {
        this.video = video;
    }


    public LocalDateTime getDate_posted() {
        return date_posted;
    }


    public void setDate_posted() {
        this.date_posted = LocalDateTime.now();
    }


    public UserEntity getAuthor() {
        return author;
    }


    public void setAuthor(UserEntity author) {
        this.author = author;
    }
    
    



}
