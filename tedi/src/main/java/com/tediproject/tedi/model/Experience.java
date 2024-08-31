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

@Entity
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;

    @Column
    private String experience;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "user_experience",joinColumns = @JoinColumn(name = "experience_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List <UserEntity> experienced_users;

    public Experience() {
        this.experienced_users = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public List<UserEntity> getExperiencedUsers() {
        return experienced_users;
    }

    public void getExperiencedUsers(List<UserEntity> experienced_users) {
        this.experienced_users = experienced_users;
    }
}
