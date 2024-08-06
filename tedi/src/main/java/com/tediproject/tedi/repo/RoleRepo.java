package com.tediproject.tedi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tediproject.tedi.model.Role;


public interface  RoleRepo extends JpaRepository<Role, Long> {
    Role findById(long role_id);
    Role findByRole(String role);
}
