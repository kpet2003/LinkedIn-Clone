package com.tediproject.tedi.service;

import com.tediproject.tedi.model.Request;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.RequestRepo;
import com.tediproject.tedi.repo.UserRepo;

public class NetworkService {
    private UserRepo userRepo;
    private RequestRepo requestRepo;

    public void createRequest(long receiver_id, String sender_email) {
        
        UserEntity sender = userRepo.findByEmail(sender_email);
        long sender_id = sender.getID(); 
        
        Request new_request = new Request();
        new_request.set_receiver(receiver_id);
        new_request.set_sender(sender_id);
        new_request.set_date();

        requestRepo.save(new_request);
    }



}
