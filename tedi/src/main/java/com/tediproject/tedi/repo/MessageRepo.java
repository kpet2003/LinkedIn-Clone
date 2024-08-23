package com.tediproject.tedi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.tediproject.tedi.model.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverIdOrderByDate(long senderId, long receiverId);
    List<Message> findByReceiverIdAndSenderIdOrderByDate(long receiverId, long senderId);
}