package com.tediproject.tedi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tediproject.tedi.model.Experience;

public interface ExperienceRepo extends JpaRepository<Experience,Long> {
    Experience findByExperience(String experience);
}
