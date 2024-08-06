package com.tediproject.tedi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.service.AdminService;

@RestController
public class AdminControllers {
    @Autowired
    private AdminService admin;
    
    @GetMapping(value = "/AdminPage/")
    public List<UserEntity> getUsers() {
        return admin.findAllUsers();   
    }
}
