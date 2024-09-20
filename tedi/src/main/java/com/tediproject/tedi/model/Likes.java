package com.tediproject.tedi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// Class Representation of the likes table
@Entity
public class Likes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article") 
    private Article article;        // the article that was liked

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user") 
    private UserEntity user;    // user who liked the article


    public Likes() {}
    

    public long getId() {
        return id;
    }


    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
