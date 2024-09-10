package com.tediproject.tedi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tediproject.tedi.model.Job;
import com.tediproject.tedi.model.UserEntity;

public interface  JobRepo extends JpaRepository<Job, Long> {
    List <Job> findByAuthor(UserEntity author);
    List <Job> findByAuthorIn(List<UserEntity> connections);
    Job findById(long id);
}
