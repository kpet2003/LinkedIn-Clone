package com.tediproject.tedi.dto;

public class UserDto {
    protected long id;
    protected String first_name;
    protected String last_name;
    protected String email;
    protected long phone_number;
    protected String image;
    protected byte[] resume;
    protected Boolean admin = false;
    protected String workExperience;
    protected String education;
    protected String skills;
    protected Boolean isPublicWorkExperience = true;
    protected Boolean isPublicEducation = true;
    protected Boolean isPublicSkills = true;

    public UserDto() {}

    public void setId(long id){
        this.id = id;
    }

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

    public byte[] getResume(){
        return this.resume;
    }

    public Boolean getAdmin(){
        return this.admin;
    }

    public Long getID(){
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
}
