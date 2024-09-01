package com.tediproject.tedi.dto;

public class RequestDto {

    private Long sender_id;
   

    private String firstName;
    private String lastName;
    private byte[] profilePicture;
    public RequestDto() {}
    

    public Long getSender_id() {
        return sender_id;
    }
    public void setSender_id(Long sender_id) {
        this.sender_id = sender_id;
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
    public byte[] getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

}
