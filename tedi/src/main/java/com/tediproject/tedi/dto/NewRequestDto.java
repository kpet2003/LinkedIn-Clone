package com.tediproject.tedi.dto;

public class NewRequestDto {
    String token;
    long user_id;
    
    

    public NewRequestDto() {}

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public long getUser_id() {
        return user_id;
    }
    
}
