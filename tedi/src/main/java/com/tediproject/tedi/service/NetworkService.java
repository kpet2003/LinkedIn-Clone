package com.tediproject.tedi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.dto.NetworkDto;
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

    public List<NetworkDto> getUsers() {
        List <UserEntity> users = userRepo.findAll();
        List <NetworkDto> final_users = new ArrayList<>();

        for(int i=0; i<users.size(); i++) {
            NetworkDto user = new NetworkDto();
            user.setId(users.get(i).getID());
            user.setFirstName(users.get(i).getFirstName());
            user.setLastName(users.get(i).getLastName());
            user.setEmail(users.get(i).getEmail());
            user.setProfilePicture(users.get(i).getProfilePicture());
            user.setWorkTitle(users.get(i).getWorkTitle());
            user.setWorkplace(users.get(i).getWorkplace());
            final_users.addLast(user);
        }



        return final_users; 

    }



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
    public List<NetworkDto> findUsers(String token) {
        UserEntity myuser = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<UserEntity> receivers = requestRepo.findReceivers(myuser);
        List<Long> receiver_ids = receivers.stream().map(UserEntity::getID).collect(Collectors.toList());
        List<UserEntity> senders = userRepo.findAllById(receiver_ids);

        List <NetworkDto> final_users = new ArrayList<>();

        for(int i=0; i<senders.size(); i++) {
            NetworkDto user = new NetworkDto();
            user.setId(senders.get(i).getID());
            user.setFirstName(senders.get(i).getFirstName());
            user.setLastName(senders.get(i).getLastName());
            user.setEmail(senders.get(i).getEmail());
            user.setProfilePicture(senders.get(i).getProfilePicture());
            user.setWorkTitle(senders.get(i).getWorkTitle());
            user.setWorkplace(senders.get(i).getWorkplace());
            final_users.addLast(user);
        }



        return final_users;
        

        

    }
    

    private List<NetworkDto> getConnectedUsers(UserEntity myuser) {
        List<UserEntity> user_bs = connectionRepo.findUserB(myuser);
        List <UserEntity> user_as = connectionRepo.findUserA(myuser);

        List<UserEntity> connectedUsers = new ArrayList<>();
        connectedUsers.addAll(user_bs);
        connectedUsers.addAll(user_as);

        List <NetworkDto> final_users = new ArrayList<>();

        for(int i=0; i<connectedUsers.size(); i++) {
            NetworkDto user = new NetworkDto();
            user.setId(connectedUsers.get(i).getID());
            user.setFirstName(connectedUsers.get(i).getFirstName());
            user.setLastName(connectedUsers.get(i).getLastName());
            user.setEmail(connectedUsers.get(i).getEmail());
            user.setProfilePicture(connectedUsers.get(i).getProfilePicture());
            user.setWorkTitle(connectedUsers.get(i).getWorkTitle());
            user.setWorkplace(connectedUsers.get(i).getWorkplace());
            final_users.addLast(user);
        }



        return final_users;

        
    }

    public List<UserEntity> findUserConnections(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        List<UserEntity> user_bs = connectionRepo.findUserB(user);
        List <UserEntity> user_as = connectionRepo.findUserA(user);

        List<UserEntity> connectedUsers = new ArrayList<>();
        connectedUsers.addAll(user_bs);
        connectedUsers.addAll(user_as);

        return connectedUsers;
    }


    public List<UserEntity> findUserConnections(UserEntity user) {
       

        List<UserEntity> user_bs = connectionRepo.findUserB(user);
        List <UserEntity> user_as = connectionRepo.findUserA(user);

        List<UserEntity> connectedUsers = new ArrayList<>();
        connectedUsers.addAll(user_bs);
        connectedUsers.addAll(user_as);

        return connectedUsers;
    }

    // find the connections of a user
    public List<NetworkDto> findConnections(String token) {
        
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<NetworkDto> connections  = this.getConnectedUsers(user);
    

        return connections;
        
    }
    // find the connections of a user
    public List<NetworkDto> findConnectionsById(Long id ) {
        
        UserEntity user = userRepo.findById(id).get();
        List<NetworkDto> connections  = this.getConnectedUsers(user);
    
        return connections;
        
    }

  

}
