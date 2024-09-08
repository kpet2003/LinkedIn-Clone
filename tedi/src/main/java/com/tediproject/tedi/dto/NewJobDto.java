package com.tediproject.tedi.dto;

import java.util.List;

public class NewJobDto {
    private String authorToken;
    private String jobTitle;
    private String jobDesc;
    private List<String> jobSkills;
    
    public NewJobDto() {
    }

    public String getAuthorToken() {
        return authorToken;
    }
    public void setAuthorToken(String authorToken) {
        this.authorToken = authorToken;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public String getJobDesc() {
        return jobDesc;
    }
    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }
    public List<String> getJobSkills() {
        return jobSkills;
    }
    public void setJobSkills(List<String> jobSkills) {
        this.jobSkills = jobSkills;
    }
            
}
