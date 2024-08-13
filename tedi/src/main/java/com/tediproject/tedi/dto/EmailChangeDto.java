package com.tediproject.tedi.dto;

public class EmailChangeDto {
    private String token;
    private String newEmail;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
