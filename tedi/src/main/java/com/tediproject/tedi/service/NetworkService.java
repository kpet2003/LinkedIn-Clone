package com.tediproject.tedi.service;

import java.util.List;
import java.util.stream.Collectors;

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



    // make a friend request
    public void createRequest(long receiver_id, String token) {

        UserEntity sender = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        UserEntity receiver = userRepo.findById(receiver_id);
    
        Request new_request = new Request();

        new_request.set_date();
        new_request.setReceiver(receiver);
        new_request.setSender(sender);

        requestRepo.save(new_request);
    }

    public void addConnection(long user_id, String token) {
        
        UserEntity user_a = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        UserEntity user_b = userRepo.findById(user_id);
        Request request = requestRepo.findByUsers(user_a, user_b);
        
        requestRepo.delete(request);
        
        Connection con = new Connection();
        con.setUser_a(user_a.getID());
        con.setUser_b(user_id);

        connectionRepo.save(con);

    }

    public void removeRequest(long user_id, String token) {
        UserEntity user_a = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        UserEntity user_b = userRepo.findById(user_id);

        Request request = requestRepo.findByUsers(user_a, user_b);
        requestRepo.delete(request);
    }

    public List<UserEntity> findUsers(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<UserEntity> receivers = requestRepo.findReceivers(user);

        List<Long> receiver_ids = receivers.stream().map(UserEntity::getID).collect(Collectors.toList());


        List<UserEntity> senders = userRepo.findAllById(receiver_ids);
        return senders;

    }

    public List<UserEntity> findConnections(String token) {
        
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        Long user_id = user.getID();
        List<Long> connected_ids = connectionRepo.findByUser(user_id);
        List<UserEntity> connections = userRepo.findAllById(connected_ids);

        return connections;
        
    }

    public List<UserEntity> findConnectionsById(Long id ) {
        
        List<Long> connected_ids = connectionRepo.findByUser(id);
        List<UserEntity> connections = userRepo.findAllById(connected_ids);
        return connections;
        
    }

}
