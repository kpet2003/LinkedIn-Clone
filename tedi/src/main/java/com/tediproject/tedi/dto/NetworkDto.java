package com.tediproject.tedi.dto;

// info about the connections returned to the frontend
public class NetworkDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private byte[] profilePicture;
    private String workTitle;
    private String workplace;
    
    public NetworkDto() {}
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public byte[] getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
    public String getWorkTitle() {
        return workTitle;
    }
    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }
    public String getWorkplace() {
        return workplace;
    }
    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }
}
