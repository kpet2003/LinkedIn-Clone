package com.tediproject.tedi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.service.NetworkService;

public class NetworkController {

    UserRepo userRepo;
    NetworkService networkService;

    @PostMapping(value = "/Network/newRequest")
    public ResponseEntity<?> newRequest(@RequestParam(value="user_id", required = false) long  id, @RequestParam(value ="sender_email", required= false) String email) {
        try {
            networkService.createRequest(id,email);
            return ResponseEntity.status(HttpStatus.OK).build();
        } 
        
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
        
}
