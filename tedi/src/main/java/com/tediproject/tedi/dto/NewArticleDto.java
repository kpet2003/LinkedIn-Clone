
package com.tediproject.tedi.dto;


public class NewArticleDto {
    
    private String author_token;




    private String article_content;
    private byte[] image;
    private byte[] video;

    private String title;

    public NewArticleDto() {}
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor_token(String author_token) {
        this.author_token = author_token;
    }
    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

   

    public String getAuthor_token() {
        return author_token;
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
