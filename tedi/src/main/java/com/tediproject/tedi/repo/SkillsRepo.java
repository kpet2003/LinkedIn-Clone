package com.tediproject.tedi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tediproject.tedi.model.Skills;

public interface SkillsRepo extends JpaRepository<Skills,Long> {
    // fetch skill by skill string
    Skills findBySkill(String skill);
}
