package com.tediproject.tedi.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "connection")
public class Connection {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    protected long id;
    

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "user_b") 
    private UserEntity user_b;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_a") 
    private UserEntity user_a;

    public Connection() {}

    public long getId() {
        return id;
    }

    public UserEntity getUser_b() {
        return user_b;
    }

    public void setUser_b(UserEntity user_b) {
        this.user_b = user_b;
    }

    public UserEntity getUser_a() {
        return user_a;
    }

    public void setUser_a(UserEntity user_a) {
        this.user_a = user_a;
    }

   
}
