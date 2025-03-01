package com.tediproject.tedi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tediproject.tedi.model.Notification;
import com.tediproject.tedi.model.UserEntity;

public interface NotificationRepo extends JpaRepository<Notification, Long>{

        // find all post notifications for user(excluding the cases where the user has liked or commented on their own article)
        @Query("select n from Notification n where n.receiver = ?1 and n.sender <> ?1")
        List<Notification> findByReceiver(UserEntity receiver);
}
