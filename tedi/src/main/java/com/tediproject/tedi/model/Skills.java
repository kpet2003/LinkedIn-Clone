package com.tediproject.tedi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "job_skills",  // Make sure this matches the Job entity's @JoinTable
        joinColumns = @JoinColumn(name = "skill_id"),
        inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private List <Job> jobs_related;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "user_skills",joinColumns = @JoinColumn(name = "skill_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List <UserEntity> skilled_users;

    public Skills() {
        this.skilled_users = new ArrayList<>();
        this.jobs_related = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public List<Job> getJobs_related() {
        return jobs_related;
    }

    public void setJobs_related(List<Job> jobs_related) {
        this.jobs_related = jobs_related;
    }

    public List<UserEntity> getSkilled_users() {
        return skilled_users;
    }

    public void setSkilled_users(List<UserEntity> skilled_users) {
        this.skilled_users = skilled_users;
    }

}
