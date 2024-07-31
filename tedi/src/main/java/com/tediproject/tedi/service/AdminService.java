package com.tediproject.tedi.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.model.User;
import com.tediproject.tedi.repo.UserRepo;

@Service
public class AdminService {
   
    @Autowired
    private UserRepo userRepo;

    public List<User> findAllUsers() {
        return userRepo.findAll();   
    }

}
