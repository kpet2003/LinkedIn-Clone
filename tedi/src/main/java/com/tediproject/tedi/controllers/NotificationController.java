package com.tediproject.tedi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tediproject.tedi.model.Notification;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.service.NotificationService;

@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping(value = "/Notifications/Requests")
    public List<UserEntity> getRequests(@RequestParam(value="token", required = false)String token ) {
        return notificationService.findUsers(token);
    }

    @GetMapping(value = "/Notifications/PostNotifications")
    public List<Notification> getNotifications(@RequestParam(value="token", required = false)String token ) {
        return notificationService.findNotifications(token);
    }

}
