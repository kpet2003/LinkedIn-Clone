package com.tediproject.tedi.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class UserDto {
    protected String first_name;
    protected String last_name;
    protected String email;
    protected long phone_number;
    protected String image;
    protected String workExperience;
    protected String education;
    protected Boolean isPublicWorkExperience;
    protected Boolean isPublicEducation;
    protected Boolean isPublicSkills;
    protected String workTitle;
    protected String workplace;
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto() {}

    public void setFirstName(String name){
        this.first_name = name;
    }

    public void setLastName(String name){
        this.last_name = name;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }

    public void setPhoneNumber(Long num){
        this.phone_number = num;
    }

    public void setProfilePicture(String pfp){
        this.image = pfp;
    }

    public void setWorkExperience(String experience){
        this.workExperience = experience;
    }

    public void setEducation(String edu){
        this.education = edu;
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

    public String getFirstName(){
        return this.first_name;
    }

    public String getLastName(){
        return this.last_name;
    }

    public String getEmail() {
        return this.email;
    }

    public Long getPhoneNumber(){
        return this.phone_number;
    }

    public String getProfilePicture(){
        return this.image;
    }   

    public String getWorkExperience(){
        return this.workExperience;
    }

    public String getEducation(){
        return this.education;
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

}
