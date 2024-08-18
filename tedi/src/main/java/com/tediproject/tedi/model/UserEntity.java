package com.tediproject.tedi.model;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name="user")
public class UserEntity {
    
    // primary key of UserEntity table
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
    protected byte[] image;
    
    @Lob
    @Column(length=100000)
    protected byte[] resume;
    
    @Column
    protected Boolean admin = false;

    @Column
    protected String workExperience;

    @Column
    protected String education;

    @Column
    protected String skills;

    @Column
    protected Boolean isPublicWorkExperience = true;

    @Column
    protected Boolean isPublicEducation = true;

    @Column
    protected Boolean isPublicSkills = true;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    protected Role role;

    @Column
    protected String workplace;


    @Column
    protected String workTitle;

    @Column
    protected String website;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<Article> articles_written;


    public UserEntity() {}

    
    public void setFirstName(String name){
        this.first_name = name;
    }

    public void setLastName(String name){
        this.last_name = name;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }

    public void setPassword(String word){
        this.password = word;
    }

    public void setPhoneNumber(Long num){
        this.phone_number = num;
    }

    public void setProfilePicture(byte[] pfp){
        this.image = pfp;
    }

    public void setResume(byte[] CV){
        this.resume = CV;
    }

    public void setAdmin(){
        this.admin = true;
    }

    public void setWorkExperience(String experience){
        this.workExperience = experience;
    }

    public void setEducation(String edu){
        this.education = edu;
    }

    public void setSkills(String skill){
        this.skills = skill;
    }

    public void setPublicWork(Boolean val){
        this.isPublicWorkExperience = val;
    }

    public void setPublicEducation(Boolean val){
        this.isPublicEducation = val;
    }

    public void setPublicSkills(Boolean val){
        this.isPublicSkills = val;
    }

    public void setRoles(Role role) {
        this.role = role;
    }

    public String getFirstName(){
        return this.first_name;
    }

    public String getLastName(){
        return this.last_name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public Long getPhoneNumber(){
        return this.phone_number;
    }

    public byte[] getProfilePicture(){
        return this.image;
    }   

    public byte[] getResume(){
        return this.resume;
    }

    public Boolean getAdmin(){
        return this.admin;
    }

    public long getID(){
        return this.id;
    }

    public String getWorkExperience(){
        return this.workExperience;
    }

    public String getEducation(){
        return this.education;
    }

    public String getSkills(){
        return this.skills;
    }

    public Boolean getPublicWork(){
        return this.isPublicWorkExperience;
    }

    public Boolean getPublicEducation(){
        return this.isPublicEducation;
    }

    public Boolean getPublicSkills(){
        return this.isPublicSkills;
    }

    public Role getRole() {
        return this.role;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }



}
