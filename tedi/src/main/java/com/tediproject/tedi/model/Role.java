package com.tediproject.tedi.model;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    protected long id;
    
    @Column
    protected String role;

    @OneToMany(mappedBy = "role", orphanRemoval = true,fetch = FetchType.EAGER)
    protected List<UserEntity> users;
    
    public void setRole(String role) {
        this.role = role;
    }

    public void addUser(UserEntity user) {
        this.users.add(user);
    }

    public String getRole() {
        return this.role;
    }


}
