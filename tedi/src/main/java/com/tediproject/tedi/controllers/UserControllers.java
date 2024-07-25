package com.tediproject.tedi.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.tediproject.tedi.repo.UserRepo;

@RestController
public class UserControllers {
    
    @Autowired
    private UserRepo user_repo;

    // @GetMapping(value="/")
    // public List <User> get_users() {
    //     return user_repo.findAll();
    // }
}
