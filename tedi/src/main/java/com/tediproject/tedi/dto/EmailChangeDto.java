package com.tediproject.tedi.dto;

public class EmailChangeDto {
    private String token;
    private String newEmail;

    public String getNewEmail(){
        return this.newEmail;
    }

    public String getToken(){
        return this.token;
    }

    public void setNewEmail(String email){
        this.newEmail = email;
    }

    public void setToken(String token){
        this.token = token;
    }
}
