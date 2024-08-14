package com.tediproject.tedi.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.RequestRepo;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.security.JwtUtil;


@Service
public class NotificationService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private JwtUtil jwtUtil;


    public List<UserEntity> findUsers(String token) {
        
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<Long> sender_ids = requestRepo.findSenders(user.getID());
        List<UserEntity> senders = userRepo.findAllById(sender_ids);
        return senders;
        
    }
}
