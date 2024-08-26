package com.tediproject.tedi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.ConnectionRepo;
import com.tediproject.tedi.repo.JobRepo;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.security.JwtUtil;

@Service
public class JobService {
    
    @Autowired
    UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired 
    ConnectionRepo connectionRepo;

    @Autowired 
    JobRepo jobRepo;

    public void createJob(String token) {
        UserEntity author = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        

    }



}
