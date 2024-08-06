package com.tediproject.tedi.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.UserRepo;

@Service
public class AdminService {
   
    @Autowired
    private UserRepo userRepo;

    public List<UserEntity> findAllUsers() {
        return userRepo.findAll();   
    }

}
