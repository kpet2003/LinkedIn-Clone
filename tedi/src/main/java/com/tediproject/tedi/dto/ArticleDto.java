package com.tediproject.tedi.dto;
import java.util.List;




public class ArticleDto {
    
    Long id;
    Long authorId;
    public Long getAuthorId() {
        return authorId;
    }
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    String authorFirstName;
    String authorLastName;
    byte[] profilePicture;

    String title;
    String content;
    byte[] picture;
    byte[] video;

    List <CommentDto> comments;

    Long comments_count;
    Long likes_count;

    Boolean isLikedByUser;
    Boolean showComments;
    

  
    public List<CommentDto> getComments() {
        return comments;
    }
    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
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

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public byte[] getPicture() {
        return picture;
    }
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
    public byte[] getVideo() {
        return video;
    }
    public void setVideo(byte[] video) {
        this.video = video;
    }
    public String getAuthorFirstName() {
        return authorFirstName;
    }
    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }
    public String getAuthorLastName() {
        return authorLastName;
    }
    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }
    public byte[] getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}
