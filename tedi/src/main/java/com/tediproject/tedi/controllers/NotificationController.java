package com.tediproject.tedi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tediproject.tedi.dto.NotificationDto;
import com.tediproject.tedi.dto.RequestDto;
import com.tediproject.tedi.service.NotificationService;

@RestController
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    // return the connection requests to the frontend
    @GetMapping(value = "/Notifications/Requests")
    public List<RequestDto> getRequests(@RequestParam(value="token", required = false)String token ) {
        return notificationService.findRequests(token);
    }

    // return the post notifications to the frontend
    @GetMapping(value = "/Notifications/PostNotifications")
    public List<NotificationDto> getNotifications(@RequestParam(value="token", required = false)String token ) {
        return notificationService.findNotifications(token);
    }

}
