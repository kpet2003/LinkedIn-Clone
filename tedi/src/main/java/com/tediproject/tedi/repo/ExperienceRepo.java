package com.tediproject.tedi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tediproject.tedi.model.Experience;

public interface ExperienceRepo extends JpaRepository<Experience,Long> {
    // fetch experience by experience string
    Experience findByExperience(String experience);
}
