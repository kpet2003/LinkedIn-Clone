package com.tediproject.tedi.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tediproject.tedi.dto.InChatUserDto;
import com.tediproject.tedi.model.Message;
import com.tediproject.tedi.repo.MessageRepo;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.security.JwtUtil;
import com.tediproject.tedi.service.MessageService;
import com.tediproject.tedi.service.NetworkService;

@Controller
public class MessageController {
    
    //needed components
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private NetworkService netService;

    @Autowired
    private MessageRepo messRepo;

    @Autowired
    private MessageService messService;

    //send messages in real time
    @MessageMapping(value="/chat")
    public Message receiveMessage(@Payload Message message, Principal principal){

        //create a new message to save in the database for chat history
        Message mess = new Message();
        mess.setSenderId(message.getSenderId());
        mess.setReceiverId(message.getReceiverId());
        mess.setMessage(message.getMessage());
        mess.setDate(message.getDate());
        messRepo.save(mess);
        
        //send the message
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(message.getReceiverId()), "/chat", message);

        return message;
    }

    //get user's data for the messages page
    @GetMapping(value="/Messages")
    public ResponseEntity<?> getInChatUserData(@RequestParam(value="token", required = false) String token){
        try{
            jwtUtil.validateToken(token); //make sure user is validated
            InChatUserDto chatUser = messService.getUserData(token); //get user data
            
            return ResponseEntity.ok(chatUser); //send dto to frontend
        }
        catch(Exception e){ //if error occurs throw exception
            return ResponseEntity.badRequest().body("token is required");
        }
    }

    //get chat history of 2 users
    @GetMapping(value="/chathistory/{user1}/{user2}")
    public ResponseEntity<?> getChatHistory(@PathVariable long user1, @PathVariable long user2){
        try {
            List<Message> history = messService.getChatHistory(user1, user2);
            return ResponseEntity.ok(history);
        } catch (Exception e) { //if error return error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //get the profile picture of the user the current user is messaging
    @GetMapping(value="/image/{userId}")
    public ResponseEntity<?> getReceiverPfp(@PathVariable long userId){
        try {
            String image = messService.getImage(userId);
            if (image != null) {
                return ResponseEntity.ok(image);
            }
            else{
                return ResponseEntity.ok(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
