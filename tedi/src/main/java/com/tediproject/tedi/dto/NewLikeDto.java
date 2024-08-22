package com.tediproject.tedi.dto;

public class NewLikeDto {
    
    private String user_token;
    private Long article_id;
    
    public NewLikeDto() {}

    public String getUser_token() {
        return user_token;
    }
    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
    public Long getArticle_id() {
        return article_id;
    }
    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

}
