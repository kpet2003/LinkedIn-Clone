package com.tediproject.tedi.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "connection")
public class Connection {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    protected long id;
    

    // ids of the users in the connection
    @Column
    protected long user_a;
    
    @Column
    protected long user_b;

    public Connection() {}

    public long getId() {
        return id;
    }

    public long getUser_a() {
        return user_a;
    }

    public long getUser_b() {
        return user_b;
    }

    public void setUser_a(long user_a) {
        this.user_a = user_a;
    }

    public void setUser_b(long user_b) {
        this.user_b = user_b;
    }

}
