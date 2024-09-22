package com.tediproject.tedi.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.dto.InChatUserDto;
import com.tediproject.tedi.model.Message;
import com.tediproject.tedi.model.UserEntity;
import com.tediproject.tedi.repo.MessageRepo;
import com.tediproject.tedi.repo.UserRepo;
import com.tediproject.tedi.security.JwtUtil;

@Service
public class MessageService {

    //needed components
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private NetworkService netService;

    @Autowired
    private MessageRepo messageRepo;

    //return the chat history of 2 users
    public List<Message> getChatHistory(long user1, long user2) {
        List<Message> messages = new ArrayList<>(); //create new list for the messages
        messages.addAll(messageRepo.findBySenderIdAndReceiverIdOrderByDate(user1, user2)); //get all messages user1 sent to user2
        messages.addAll(messageRepo.findByReceiverIdAndSenderIdOrderByDate(user1, user2)); //get all messages user2 sent to user1
        messages.sort(Comparator.comparing(Message::getDate)); //sort messages by date
        return messages; //return list of messages
    }

    public InChatUserDto getUserData(String token){
        UserEntity user = userRepo.findByEmail(jwtUtil.getEmailFromJWT(token)); //find user
        InChatUserDto chatUser = new InChatUserDto(); //make new dto
        if(user != null){ //if user was found
            //add user data to dto
            chatUser.setId(user.getID());
            chatUser.setFirst_name(user.getFirstName());
            chatUser.setLast_name(user.getLastName());
            chatUser.setEmail(user.getEmail());
            chatUser.setLastChatUserId(user.getLastChatUserId());
            if (user.getProfilePicture() != null) {
                String base64Image = Base64.getEncoder().encodeToString(user.getProfilePicture());
                chatUser.setImage(base64Image);
            }
            List<UserEntity> connectedUsers = netService.findUserConnections(token); //get user's connections
            List<InChatUserDto> temp = new ArrayList<>(); //create a list of dtos for the connections' data
            for(UserEntity tempUser : connectedUsers){ //for every user in the connections
                Boolean messaged = true; //set that they have messaged they user
                if(getChatHistory(user.getID(), tempUser.getID()).isEmpty()){ //if the chat history between the 2 users is empty
                    messaged = false; //set that they have not messaged the user
                }
                //create a new dto for them
                InChatUserDto tempchatUser = new InChatUserDto(tempUser.getFirstName(), tempUser.getLastName(),
                tempUser.getEmail(), null, tempUser.getID(), messaged, tempUser.getLastChatUserId(), null);
                if (tempUser.getProfilePicture() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(tempUser.getProfilePicture());
                    tempchatUser.setImage(base64Image);
                }
                
                temp.add(tempchatUser); //add to list
            }
            chatUser.setConnections(temp); //set the user's connections as the list that was created
        }
        else{
            chatUser = null; //if user not found set dto to null
        }

        return chatUser;
    }

    //return a user's profile picture
    public String getImage(long id){
        UserEntity user = userRepo.findById(id);
        if (user.getProfilePicture() != null) { //if not null, convert profile picture to a base 64 string
            String base64Image = Base64.getEncoder().encodeToString(user.getProfilePicture());
            return base64Image;
        }
        return null;
    }
}
