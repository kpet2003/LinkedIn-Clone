package com.tediproject.tedi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

// Class Representation of the education table
@Entity
public class Education {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;

    @Column
    private String education;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "user_education",joinColumns = @JoinColumn(name = "education_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List <UserEntity> educated_users;

    public Education() {
          this.educated_users = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public List<UserEntity> getEducated_users() {
        return educated_users;
    }

    public void setEducated_users(List<UserEntity> educated_users) {
        this.educated_users = educated_users;
    }
    
}
