package com.tediproject.tedi.dto;

// info about the user's experience returned to the frontend
public class ExperienceDto {
    private long id;
    private String experience;
    public ExperienceDto() {
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getExperience() {
        return experience;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }
}
