package com.tediproject.tedi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tediproject.tedi.model.Education;

public interface EducationRepo extends JpaRepository<Education,Long> {
    Education findByEducation(String education);
}
