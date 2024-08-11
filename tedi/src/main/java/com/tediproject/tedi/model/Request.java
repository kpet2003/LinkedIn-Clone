package com.tediproject.tedi.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime; 

@Entity
public class Request {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    protected long id;
    

    // id of the user that sends the friend request
    @Column
    protected long sender;

    // id of the user that receives the request
    @Column
    protected long receiver;

    // date that the request was made
    @Column
    protected LocalDateTime date_sent;

    Request() {}





}
