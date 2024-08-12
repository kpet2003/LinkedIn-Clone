package com.tediproject.tedi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.model.Request;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.RequestRepo;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.security.JwtUtil;

@Service
public class NetworkService {
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private JwtUtil jwtUtil;


    public void createRequest(long receiver_id, String token) {

        UserEntity sender = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        long sender_id = sender.getID(); 
        
        Request new_request = new Request();
        new_request.set_receiver(receiver_id);
        new_request.set_sender(sender_id);
        new_request.set_date();



        requestRepo.save(new_request);
    }



}
