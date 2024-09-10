package com.tediproject.tedi.dto;

public class EducationDto {
    private  long id;
    private String education;
    
    public EducationDto() {}
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }
}
