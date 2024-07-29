package com.tediproject.tedi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tediproject.tedi.model.User;

public interface UserRepo extends JpaRepository<User, Long>{
    User findUser(String email);
}
