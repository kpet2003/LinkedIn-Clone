package com.tediproject.tedi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tediproject.tedi.model.Skills;

public interface SkillsRepo extends JpaRepository<Skills,Long> {
    Skills findBySkill(String skill);
}
