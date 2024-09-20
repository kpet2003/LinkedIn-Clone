package com.tediproject.tedi.types;

// class containing article id and the rating of the article according to the recommendation system
public class ArticlePair {
    private long id;
    private double rating;
    
    public ArticlePair() {}
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
}
