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
        List<Long> user_ids = requestRepo.findReceiver(user.getID());

        List <UserEntity> users =  new ArrayList<UserEntity>();
        
        for (int i=0; i<user_ids.size(); i++) {
            long user_id = user_ids.get(i);
            UserEntity temp = userRepo.findById(user_id);
            users.add(temp);
        }
    

        return users;

        

    }
}
