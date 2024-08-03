package com.tediproject.tedi.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tediproject.tedi.model.User;
import com.tediproject.tedi.service.UserService;

@RestController
public class UserControllers {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/SignUp/signup")
    public ResponseEntity<?> createUser(
        @RequestParam(value="email", required = false) String email,
        @RequestParam(value="firstName", required = false) String firstName,
        @RequestParam(value="lastName", required = false) String lastName,
        @RequestParam(value="password", required = false) String password,
        @RequestParam(value="phoneNumber", required = false) Long phoneNumber,
        @RequestPart(value = "profilePicture", required = false) MultipartFile pfp,
        @RequestPart(value = "resume", required = false) MultipartFile cv) {
        try {
            User createdUser = userService.createUser(firstName, lastName, email, password, phoneNumber, pfp, cv);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(value= "/Login")
    public ResponseEntity<?> login(
        @RequestParam(value="email", required = false) String email,
        @RequestParam(value="password", required = false) String password){
        try {
            Long id = userService.loginUser(email, password);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping(value = "/NewEmail")
    public ResponseEntity<?> getUser(@RequestParam(value="id", required = false) Long id) {
        try{
<<<<<<< HEAD
=======
            System.out.println("id is " + id);
>>>>>>> bf8532d1437711eaa85a2bc4c8f0b6712d657a53
            User user = userService.getUserById(id);
        
        if(user != null){
            return ResponseEntity.ok(user);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("ID is required");
        }
    }

}