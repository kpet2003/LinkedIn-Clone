package com.tediproject.tedi.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime; 

@Entity
public class Request {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    protected long id;
    


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver") 
    private UserEntity receiver;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender") 
    private UserEntity sender;

    // date that the request was made
    @Column
    protected LocalDateTime date_sent;

    public Request() {}
    
    public long getId() {
        return id;
    }
   

  
    public LocalDateTime getDate_sent() {
        return date_sent;
    }



    public void set_date() {
        this.date_sent = LocalDateTime.now();
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }




}
