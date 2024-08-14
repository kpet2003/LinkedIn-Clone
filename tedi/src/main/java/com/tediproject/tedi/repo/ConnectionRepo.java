package com.tediproject.tedi.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tediproject.tedi.model.Connection;



public interface ConnectionRepo extends JpaRepository<Connection, Long>{
    
    // find all connections with user
    @Query("select case when c.user_a = ?1 then c.user_b else c.user_a end from Connection c where c.user_a = ?1 or c.user_b = ?1")
    List<Long> findByUser(long user_id);
    
}
