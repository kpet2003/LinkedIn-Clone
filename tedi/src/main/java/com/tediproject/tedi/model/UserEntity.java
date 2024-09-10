package com.tediproject.tedi.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
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
    

    @Column
    protected Boolean admin = false;



    @Column
    protected Boolean isPublicWorkExperience = true;

    @Column
    protected Boolean isPublicEducation = true;

    @Column
    protected Boolean isPublicSkills = true;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    protected Role role;

    @Column
    protected String workplace;


    @Column
    protected String workTitle;

   
    @JsonManagedReference
    @ManyToMany(mappedBy = "educated_users", fetch = FetchType.EAGER)
    private List <Education> user_education;

    @JsonManagedReference
    @ManyToMany(mappedBy = "experienced_users", fetch = FetchType.EAGER)
    private List <Experience> user_experience;


    public List<Experience> getUser_experience() {
        return user_experience;
    }


    public void setUser_experience(List<Experience> user_experience) {
        this.user_experience = user_experience;
    }


    public List<Education> getUser_education() {
        return user_education;
    }


    public void setUser_education(List<Education> user_education) {
        this.user_education = user_education;
    }

    @OneToMany(mappedBy = "author", orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<Article> articles_written;



    @OneToMany(mappedBy = "user", orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<Likes> likes_posted;


    @OneToMany(mappedBy = "poster", orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<Comments> comments_posted;

   
    @OneToMany(mappedBy = "receiver", orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<Request> requests_received;

    
    @OneToMany(mappedBy = "sender", orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<Request> requests_sent;

   
    @OneToMany(mappedBy = "receiver", orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<Notification> notifications_received;

   
    @OneToMany(mappedBy = "sender", orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<Notification> notifications_sent;

    
    @OneToMany(mappedBy = "user_a", orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<Connection> connections_requested;

    
    @OneToMany(mappedBy = "user_b", orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<Connection> connections_accepted;

    
    @OneToMany(mappedBy = "author", orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<Job> jobs_posted;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
    name = "job_applicants",
    joinColumns = @JoinColumn(name = "applicant"),
    inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    protected List<Job> jobs_applied;

   
    @ManyToMany(mappedBy = "skilled_users", fetch = FetchType.EAGER)
    private List <Skills> user_skills;


    public List<Job> getJobs_posted() {
        return jobs_posted;
    }


    public void setJobs_posted(List<Job> jobs_posted) {
        this.jobs_posted = jobs_posted;
    }


    public List<Job> getJobs_applied() {
        return jobs_applied;
    }


    public void setJobs_applied(List<Job> jobs_applied) {
        this.jobs_applied = jobs_applied;
    }


    public List<Skills> getUser_skills() {
        return user_skills;
    }


    public void setUser_skills(List<Skills> user_skills) {
        this.user_skills = user_skills;
    }


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

        this.password =  word ;
    }

    public void setPhoneNumber(Long num){
        this.phone_number = num;
    }

    public void setProfilePicture(byte[] pfp){
        this.image = pfp;
    }


    public void setAdmin(){
        this.admin = true;
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

    public Boolean getAdmin(){
        return this.admin;
    }

    public long getID(){
        return this.id;
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


    public void setId(Long i) {
        this.id = i;
    }




}
