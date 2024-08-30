package com.tediproject.tedi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tediproject.tedi.model.Request;
import com.tediproject.tedi.model.UserEntity;


public interface  RequestRepo  extends JpaRepository<Request, Long>{
    

    // find all requests to user
    @Query("select r.sender from Request r where r.receiver = ?1 order by r.date_sent desc")
    List <UserEntity> findSenders(UserEntity receiver);

    // find request based on users
    @Query("select r from Request r where r.receiver = ?1 and r.sender = ?2")
    Request findByUsers(UserEntity receiver,UserEntity sender);
    
    // find all the requests that a user has made
    @Query("select r.receiver from Request r where r.sender = ?1 ")
    List<UserEntity> findReceivers(UserEntity sender);
}
