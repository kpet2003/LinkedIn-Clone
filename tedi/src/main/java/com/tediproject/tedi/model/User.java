package com.tediproject.tedi.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


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
    @Column
    protected String image_path;
    @Column
    protected String resume_path;

    public User() {}

}
