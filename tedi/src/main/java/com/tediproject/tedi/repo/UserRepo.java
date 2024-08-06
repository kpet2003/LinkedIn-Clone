package com.tediproject.tedi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tediproject.tedi.model.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long>{
    UserEntity findById(long id);
    UserEntity findByEmail(String email);
}
