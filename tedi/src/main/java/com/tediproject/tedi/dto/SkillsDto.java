package com.tediproject.tedi.dto;

public class SkillsDto {
    private long id;
    private String skill;
    
    
    public SkillsDto() {}
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getSkill() {
        return skill;
    }
    public void setSkill(String skill) {
        this.skill = skill;
    }
}
