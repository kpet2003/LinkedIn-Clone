package com.tediproject.tedi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tediproject.tedi.model.Request;


public interface  RequestRepo  extends JpaRepository<Request, Long>{
    

    // find all requests to user
    @Query("select r.sender from Request r where r.receiver = ?1 order by r.date_sent desc")
    List <Long> findReceiver(long receiver_id);

    // find request based on users
    @Query("select r from Request r where r.receiver = ?1 and r.sender = ?2")
    Request findByUsers(long user_a,long user_b);
}
