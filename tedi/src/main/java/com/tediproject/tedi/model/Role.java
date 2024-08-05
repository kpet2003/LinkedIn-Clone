package com.tediproject.tedi.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;


@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    protected long id;
    
    @Column
    protected String role;
}
