package com.tediproject.tedi.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tediproject.tedi.repo.UserRepo;

@RestController
public class UserControllers {
    
    @Autowired
    private UserRepo user_repo;
    
    @PostMapping(value="/SignUp") 
    public void saveUser()  {
        
    }


}
