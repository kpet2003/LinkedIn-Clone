package com.tediproject.tedi.service;

import java.util.ArrayList;
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


    // add a new connection to the connections table
    public void addConnection(long user_id, String token) {
        
        UserEntity user_a = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        UserEntity user_b = userRepo.findById(user_id);
        Request request = requestRepo.findByUsers(user_a, user_b);
        
        requestRepo.delete(request);
        
        Connection con = new Connection();
        con.setUser_a(user_a);
        con.setUser_b(user_b);

        connectionRepo.save(con);

    }


    // handle declining of requests
    public void removeRequest(long user_id, String token) {
        UserEntity user_a = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        UserEntity user_b = userRepo.findById(user_id);

        Request request = requestRepo.findByUsers(user_a, user_b);
        requestRepo.delete(request);
    }

    // find users that have made a friend request
    public List<UserEntity> findUsers(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<UserEntity> receivers = requestRepo.findReceivers(user);

        List<Long> receiver_ids = receivers.stream().map(UserEntity::getID).collect(Collectors.toList());


        List<UserEntity> senders = userRepo.findAllById(receiver_ids);
        return senders;

    }
    

    private List<UserEntity> getConnectedUsers(UserEntity user) {
        List<UserEntity> user_bs = connectionRepo.findUserB(user);
        List <UserEntity> user_as = connectionRepo.findUserA(user);

        List<UserEntity> connectedUsers = new ArrayList<>();
        connectedUsers.addAll(user_bs);
        connectedUsers.addAll(user_as);

        return connectedUsers;
    }

    // find the connections of a user
    public List<UserEntity> findConnections(String token) {
        
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<UserEntity> connections  = this.getConnectedUsers(user);
    

        return connections;
        
    }
    // find the connections of a user
    public List<UserEntity> findConnectionsById(Long id ) {
        
        UserEntity user = userRepo.findById(id).get();
        List<UserEntity> connections  = this.getConnectedUsers(user);
    
        return connections;
        
    }

}
