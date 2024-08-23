package com.tediproject.tedi.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tediproject.tedi.model.Message;
import com.tediproject.tedi.repo.MessageRepo;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepo messageRepo;

    public List<Message> getChatHistory(long user1, long user2) {
        List<Message> messages = new ArrayList<>();
        messages.addAll(messageRepo.findBySenderIdAndReceiverIdOrderByDate(user1, user2));
        messages.addAll(messageRepo.findByReceiverIdAndSenderIdOrderByDate(user1, user2));
        messages.sort(Comparator.comparing(Message::getDate));
        return messages;
    }
}
