package com.tediproject.tedi.dto;
import java.util.List;

import com.tediproject.tedi.model.Article;
import com.tediproject.tedi.model.Comments;

public class ArticleDto {
    Article article;
    List <Comments> comments;

    Long comments_count;
    Long likes_count;

    Boolean isLikedByUser;
    Boolean showComments;
    

  
    public List<Comments> getComments() {
        return comments;
    }
    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
    public Article getArticle() {
        return article;
    }
    public void setArticle(Article article) {
        this.article = article;
    }
    public Long getComments_count() {
        return comments_count;
    }
    public void setComments_count(Long comments_count) {
        this.comments_count = comments_count;
    }
    public Long getLikes_count() {
        return likes_count;
    }
    public void setLikes_count(Long likes_count) {
        this.likes_count = likes_count;
    }
    public Boolean getIsLikedByUser() {
        return isLikedByUser;
    }
    public void setIsLikedByUser(Boolean isLikedByUser) {
        this.isLikedByUser = isLikedByUser;
    }
    public Boolean getShowComments() {
        return showComments;
    }
    public void setShowComments(Boolean showComments) {
        this.showComments = showComments;
    }
}
