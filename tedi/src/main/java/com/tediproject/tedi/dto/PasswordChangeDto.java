package com.tediproject.tedi.dto;

public class PasswordChangeDto {
    private String email;
    private String newPassword;

    public String getEmail(){
        return this.email;
    }

    public String getNewPassword(){
        return this.newPassword;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setNewPassword(String password){
        this.newPassword = password;
    }
}
