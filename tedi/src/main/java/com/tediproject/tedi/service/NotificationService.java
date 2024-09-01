package com.tediproject.tedi.service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.dto.NotificationDto;
import com.tediproject.tedi.dto.RequestDto;
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
    public List<RequestDto> findRequests(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
        List<UserEntity> senders = requestRepo.findSenders(user);
        List <RequestDto> requests = new ArrayList<>();

        for(int i=0; i<senders.size(); i++) {
            RequestDto request = new RequestDto();
            request.setSender_id(senders.get(i).getID());
            request.setFirstName(senders.get(i).getFirstName());
            request.setLastName(senders.get(i).getLastName());
            request.setProfilePicture(senders.get(i).getProfilePicture());
            requests.addLast(request);
        }
        

        return requests;
    }

    public List<NotificationDto> findNotifications(String token) {
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));

        List<Notification> article_notifications = notificationRepo.findByReceiver(user);
        article_notifications.sort(Comparator.comparing(Notification::getDate_sent).reversed());

        List<NotificationDto> notifications = new ArrayList<>();
        
        for(int i=0; i<article_notifications.size(); i++) {
            NotificationDto notification = new NotificationDto();
            notification.setId(article_notifications.get(i).getId());
            notification.setSender_id(article_notifications.get(i).getSender().getID());
            notification.setFirstName(article_notifications.get(i).getSender().getFirstName());
            notification.setLastName(article_notifications.get(i).getSender().getLastName());
            notification.setTitle(article_notifications.get(i).getArticle().getTitle());
            notification.setIsComment(article_notifications.get(i).getIsComment());
            notification.setMessage(article_notifications.get(i).getMessage());
            notification.setProfilePicture(article_notifications.get(i).getSender().getProfilePicture());
            notifications.addLast(notification);
        }

        return notifications;
    }   
}
