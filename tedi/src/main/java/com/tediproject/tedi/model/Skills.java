package com.tediproject.tedi.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;

@Entity
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;

    @Lob 
    @Column(length = 16777216)
    private String skill;

    @ManyToMany
    @JoinTable(name = "job_skills",joinColumns = @JoinColumn(name = "skill_id"),inverseJoinColumns = @JoinColumn(name = "job_id"))
    private List <Job> jobs_related;

    @ManyToMany
    @JoinTable(name = "user_skills",joinColumns = @JoinColumn(name = "skill_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List <UserEntity> skilled_users;

}
