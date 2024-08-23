package com.tediproject.tedi.controllers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tediproject.tedi.dto.InChatUserDto;
import com.tediproject.tedi.model.Message;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.security.JwtUtil;
import com.tediproject.tedi.service.NetworkService;

@Controller
public class MessageController {
    
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private NetworkService netService;

    @MessageMapping(value="/chat")
    public Message receiveMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(message.getReceiverName()),"/chat",message);
        System.out.println(message.toString());
        return message;
    }

    @GetMapping(value="/Messages")
    public ResponseEntity<?> getInChatUserData(@RequestParam(value="token", required = false) String token){
        try{
            System.out.println("IN GET");
            System.out.println("TOKEN IS "+token);
            jwtUtil.validateToken(token);
            System.out.println("VALID TOKEN");
            UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token));
            System.out.println("MAKING USER DTO");
            if(user != null){
                InChatUserDto chatUser = new InChatUserDto();
                chatUser.setId(user.getID());
                chatUser.setFirst_name(user.getFirstName());
                chatUser.setLast_name(user.getLastName());
                chatUser.setEmail(user.getEmail());
                if (user.getProfilePicture() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(user.getProfilePicture());
                    chatUser.setImage(base64Image);
                }
                System.out.println("MAKING THE CONNECTIONS NOWWWWWW");
                List<UserEntity> connectedUsers = netService.findConnections(token);
                System.out.println("GOT CONNECTIONS");
                List<InChatUserDto> temp = new ArrayList<>();
                for(UserEntity tempUser : connectedUsers){
                    InChatUserDto tempchatUser = new InChatUserDto();
                    tempchatUser.setId(tempUser.getID());
                    tempchatUser.setFirst_name(tempUser.getFirstName());
                    tempchatUser.setLast_name(tempUser.getLastName());
                    tempchatUser.setEmail(tempUser.getEmail());
                    
                    if (tempUser.getProfilePicture() != null) {
                        String base64Image = Base64.getEncoder().encodeToString(tempUser.getProfilePicture());
                        tempchatUser.setImage(base64Image);
                    }
                    
                    temp.add(tempchatUser);
                }
                chatUser.setConnections(temp);
                System.out.println("BACKEND DONE");
                return ResponseEntity.ok(chatUser);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("token is required");
        }
    }
}
