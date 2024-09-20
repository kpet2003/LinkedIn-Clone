package com.tediproject.tedi.types;

// comment and the title of the article to which it was posted (Used in adminService)
public class CommentInfo {
    private String comment;
    private String article_title;
    
    public CommentInfo() {}
    
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getArticle_title() {
        return article_title;
    }
    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }
}
