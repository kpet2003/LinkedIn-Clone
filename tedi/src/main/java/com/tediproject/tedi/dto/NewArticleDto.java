
package com.tediproject.tedi.dto;


public class NewArticleDto {
    
    private String user_token;
    private String article_content;
    private byte[] image;
    
    public NewArticleDto() {}

    public String getUser_token() {
        return user_token;
    }

    public String getArticle_content() {
        return article_content;
    }

    public void setArticle_content(String article_content) {
        this.article_content = article_content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
