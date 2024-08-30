package com.tediproject.tedi.service;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.model.Notification;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.NotificationRepo;
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

    @Autowired
    private NotificationRepo notificationRepo;


    // find users that made a friend request
    public List<UserEntity> findUsers(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<UserEntity> senders = requestRepo.findSenders(user);
        return senders;
    }

    public List<Notification> findNotifications(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        List<Notification> notifications = notificationRepo.findByReceiver(user);
        notifications.sort(Comparator.comparing(Notification::getDate_sent).reversed());

        return notifications;
    }   
}
