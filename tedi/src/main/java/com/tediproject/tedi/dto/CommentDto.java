package com.tediproject.tedi.dto;


public class CommentDto {
    private String firstName;
    private String lastName;
    private byte[] profilePicture;
    private String content;
    private Long id;
    private Long article_id;
    private Long poster_id;
    public Long getPoster_id() {
        return poster_id;
    }
    public void setPoster_id(Long poster_id) {
        this.poster_id = poster_id;
    }
    public CommentDto() {
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public byte[] getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getArticle_id() {
        return article_id;
    }
    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }
}