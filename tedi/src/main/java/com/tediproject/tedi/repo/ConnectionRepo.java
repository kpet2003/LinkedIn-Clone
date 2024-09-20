package com.tediproject.tedi.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.tediproject.tedi.model.Connection;
import com.tediproject.tedi.model.UserEntity;



public interface ConnectionRepo extends JpaRepository<Connection, Long>{
    
    // find all connections with user
    @Query("select c.user_b from Connection c where c.user_a = ?1")
    List<UserEntity> findUserB(UserEntity user_a);

    @Query("select c.user_a from Connection c where c.user_b = ?1")
    List<UserEntity> findUserA(UserEntity user_b);

    // find all connections with users
    @Query("select c from Connection c where (c.user_a = ?1 and c.user_b = ?2) or  (c.user_a = ?2 and c.user_b = ?1)")
    List<Connection> findByUsers(UserEntity user_a,UserEntity user_b);
    
}
