package com.tediproject.tedi.dto;

import java.time.LocalDateTime;

public class JobDto {
    private String author;
    private String title;
    private String desc;
    private LocalDateTime date;
    private long jobId;
    private long authorId;
    
    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public JobDto(String author, String title, String desc, LocalDateTime date, long jobId, long authorId) {
        this.author = author;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.jobId = jobId;
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    
}
