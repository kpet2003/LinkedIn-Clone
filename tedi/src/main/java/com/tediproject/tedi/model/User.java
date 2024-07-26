package com.tediproject.tedi.model;
import java.io.File;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;


@Entity
public class User {
    
    // primary key of User table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    protected long id;
    
    @Column
    protected String first_name;
    @Column
    protected String last_name;
    @Column
    protected String email;
    @Column
    protected String password;
    @Column
    protected long phone_number;
    
    @Lob
    @Column(length=100000)
    protected File image_path;
    
    @Lob
    @Column(length=100000)
    protected File resume_path;
    
    @Column
    protected Boolean admin;

    public User() {}

}
