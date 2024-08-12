package com.tediproject.tedi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tediproject.tedi.dto.NewRequestDto;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.service.NetworkService;


@RestController
public class NetworkController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    NetworkService networkService;

    @PostMapping(value = "/Network/newRequest")
    public ResponseEntity<?> newRequest(@RequestBody NewRequestDto request) {


        try {

            networkService.createRequest(request.getUser_id(),request.getToken());
            return ResponseEntity.status(HttpStatus.OK).build();
        } 
        
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
        
}
