package com.tediproject.tedi.model;

import java.time.LocalDateTime;

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
public class Notification {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    protected long id;
    

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender") 
    protected UserEntity sender;

   
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver") 
    protected UserEntity receiver;

    // date that the request was made
    @Column
    protected LocalDateTime date_sent;

    @Column
    protected Boolean isComment;

    @Lob 
    @Column(length = 16777216)
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article") 
    protected Article article;



    public Notification() {
        this.date_sent = LocalDateTime.now();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getDate_sent() {
        return date_sent;
    }


    public Boolean getIsComment() {
        return isComment;
    }

    public void setIsComment(Boolean isComment) {
        this.isComment = isComment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
