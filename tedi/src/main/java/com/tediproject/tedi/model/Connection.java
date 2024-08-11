package com.tediproject.tedi.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Connection {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    protected long id;
    

    // ids of the users in the connection
    @Column
    protected long user_a;
    
    @Column
    protected long user_b;

    public Connection() {}

}
