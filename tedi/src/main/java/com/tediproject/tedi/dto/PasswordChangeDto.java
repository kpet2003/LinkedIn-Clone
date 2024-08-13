package com.tediproject.tedi.dto;

public class PasswordChangeDto {
    private String token;
    private String newPassword;

    public String getToken() {
        return token;
    }

    public String getNewPassword(){
        return this.newPassword;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setNewPassword(String password){
        this.newPassword = password;
    }
}
