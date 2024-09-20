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

import com.tediproject.tedi.dto.NetworkDto;
import com.tediproject.tedi.dto.NewRequestDto;
import com.tediproject.tedi.service.NetworkService;


@RestController
public class NetworkController {

    @Autowired
    NetworkService networkService;

    // return a list of all users to the frontend
    @GetMapping(value = "/Network/getUsers")
    public List<NetworkDto> getUsers() {
        return networkService.getUsers();
    }

    // add the new connection request to the database
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

    // add the connection to the database and remove the request that contains the users from the database
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

    // remove the request from the database
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

    // fetch the requests for the user
    @GetMapping(value = "/Network/Requests")
    public List<NetworkDto> getRequests(@RequestParam(value="token", required = false)String token ) {
        return networkService.findUsers(token);
    }
    
    // fetch the connections of the user
    @GetMapping(value = "/Network/Connections")
    public List<NetworkDto> getConnections(@RequestParam(value="token", required = false)String token ) {
        return networkService.findConnections(token);
    }

    // fetch the connections of the user with specified id
    @GetMapping(value = "/ViewNetwork/getConnections/{id}")
    public List<NetworkDto> getNetwork(@PathVariable Long id) {

        return networkService.findConnectionsById(id);
    }
        
}
