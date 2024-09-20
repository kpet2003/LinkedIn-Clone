package com.tediproject.tedi.model;
import java.time.LocalDateTime;
import java.util.List;

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


// Class Representation of the article table
@Entity
public class Article {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;            // primary key of article table

    @Column 
    private String title;

    @Column
    private int views;

    public void setId(long id) {
        this.id = id;
    }


    public int getViews() {
        return views;
    }


    public void setViews(int views) {
        this.views = views;
    }


    @Lob 
    @Column(length = 16777216)
    private String content;

    @Lob
    @Column(length=100000)
    private byte[] picture;

    @Lob
    @Column(length=100000)
    private byte[] video;

    


    @Column(name = "date_posted")
    private LocalDateTime date_posted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author") 
    private UserEntity author;  // the article's author

    @OneToMany(mappedBy = "article",  orphanRemoval = true,fetch = FetchType.EAGER)
    List <Likes> likes;    // the likes of the article

    @OneToMany(mappedBy = "article",  orphanRemoval = true,fetch = FetchType.EAGER)
    List <Comments> comments;   // the comments posted on the article

    @OneToMany(mappedBy = "article", orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<Notification> article_notifications; // the notifications related to the article

    @Column
    private String category;    // the category of the article(used in the recommendation system)

    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public Article() {
        this.views = 0;
    }


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

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public List<Likes> getLikes() {
        return likes;
    }

    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }
    
    



}
