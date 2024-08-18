package com.tediproject.tedi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tediproject.tedi.dto.NewRequestDto;
import com.tediproject.tedi.model.UserEntity;
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

    @PostMapping(value = "/Notifications/newConnection")
    public ResponseEntity<?> newConnection(@RequestBody NewRequestDto connection) {
        try {
            networkService.addConnection(connection.getUser_id(),connection.getToken());
            return ResponseEntity.status(HttpStatus.OK).build();
        } 
        
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping(value="/Notifications/declineRequest")
    public ResponseEntity<?> declineRequest(@RequestBody NewRequestDto request) {
        try {
            networkService.removeRequest(request.getUser_id(),request.getToken());
            return ResponseEntity.status(HttpStatus.OK).build();
        } 
        
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "/Network/Requests")
    public List<UserEntity> getRequests(@RequestParam(value="token", required = false)String token ) {
        return networkService.findUsers(token);
    }

    @GetMapping(value = "/Network/Connections")
    public List<UserEntity> getConnections(@RequestParam(value="token", required = false)String token ) {
        return networkService.findConnections(token);
    }

    @GetMapping(value = "/ViewNetwork/getConnections/{id}")
    public List<UserEntity> getProfile(@PathVariable Long id) {
        return networkService.findConnectionsById(id);
    }
        
}
