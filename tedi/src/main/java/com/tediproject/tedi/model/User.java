package com.tediproject.tedi.model;
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
    protected byte[] image;
    
    @Lob
    @Column(length=100000)
    protected byte[] resume;
    
    @Column
    protected Boolean admin = false;

    public User() {}

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

    public void getProfilePicture(){
        
    }

    public void getResume(){

    }

    public Boolean getAdmin(){
        return this.admin;
    }

}
