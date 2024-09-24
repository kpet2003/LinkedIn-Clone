package com.tediproject.tedi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tediproject.tedi.dto.AdminDto;
import com.tediproject.tedi.security.JwtUtil;
import com.tediproject.tedi.service.AdminService;

@RestController
public class AdminControllers {
    @Autowired
    private AdminService admin;

    @Autowired
    private JwtUtil jwtUtil;

    
    // return all user data to the frontend
    @GetMapping(value = "/AdminPage/")
    public List<AdminDto> getUsers(@RequestParam("token")String token) {
        jwtUtil.validateToken(token);
        return admin.findAllUsers();   
    }

    // return the data of users with ids in the list user_ids to the frontend, in xml form
    @GetMapping(value = "/AdminPage/getXml",produces = "application/xml" )
    public List<AdminDto> getXmlUsers(@RequestParam("user_ids") List<Long> user_ids,@RequestParam("token")String token) {
        jwtUtil.validateToken(token);
        return admin.findUsers(user_ids);   
    }


}
