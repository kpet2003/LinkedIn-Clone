package com.tediproject.tedi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.tediproject.tedi.model.Message;

public interface ChatMessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverIdOrderByTimestamp(String senderId, String receiverId);
    List<Message> findByReceiverIdAndSenderIdOrderByTimestamp(String receiverId, String senderId);
}