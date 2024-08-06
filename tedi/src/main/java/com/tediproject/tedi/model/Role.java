package com.tediproject.tedi.model;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
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
