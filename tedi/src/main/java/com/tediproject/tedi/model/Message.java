package com.tediproject.tedi.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    protected long id;
    @Column
    private long senderName;
    @Column
    private long receiverName;
    @Column
    private String message;
    @Column
    private Date date;
    @Column
    private Status status;
    
    public Message() {
    }

    public long getSenderName() {
        return senderName;
    }

    public void setSenderName(long senderName) {
        this.senderName = senderName;
    }

    public long getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(long receiverName) {
        this.receiverName = receiverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", status=" + status +
                '}';
    }

    
}
