package com.tediproject.tedi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tediproject.tedi.dto.AdminDto;
import com.tediproject.tedi.service.AdminService;

@RestController
public class AdminControllers {
    @Autowired
    private AdminService admin;
    
    @GetMapping(value = "/AdminPage/")
    public List<AdminDto> getUsers() {
        return admin.findAllUsers();   
    }

    @GetMapping(value = "/AdminPage/getXml",produces = "application/xml" )
    public List<AdminDto> getXmlUsers(@RequestParam("user_ids") List<Long> user_ids) {
        return admin.findUsers(user_ids);   
    }


}
