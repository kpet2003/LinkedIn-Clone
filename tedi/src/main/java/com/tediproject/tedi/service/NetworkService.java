package com.tediproject.tedi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.model.Connection;
import com.tediproject.tedi.model.Request;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.ConnectionRepo;
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

    @Autowired 
    ConnectionRepo connectionRepo;


    public void createRequest(long receiver_id, String token) {

        UserEntity sender = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        long sender_id = sender.getID(); 
        
        Request new_request = new Request();
        new_request.set_receiver(receiver_id);
        new_request.set_sender(sender_id);
        new_request.set_date();

        requestRepo.save(new_request);
    }

    public void addConnection(long user_id, String token) {
        
        UserEntity user_a = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        Request request = requestRepo.findByUsers(user_a.getID(), user_id);

        requestRepo.delete(request);
        
        Connection con = new Connection();
        con.setUser_a(user_a.getID());
        con.setUser_b(user_id);

        connectionRepo.save(con);

    }

    public void removeRequest(long user_id, String token) {
        UserEntity user_a = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        Request request = requestRepo.findByUsers(user_a.getID(), user_id);
        requestRepo.delete(request);
    }

    public List<UserEntity> findUsers(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<Long> receivers = requestRepo.findReceivers(user.getID());

        List<UserEntity> senders = userRepo.findAllById(receivers);
        return senders;

    }

    public List<UserEntity> findConnections(String token) {
        
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        Long user_id = user.getID();
        List<Long> connected_ids = connectionRepo.findByUser(user_id);
        List<UserEntity> connections = userRepo.findAllById(connected_ids);

        return connections;
        
    }

}
