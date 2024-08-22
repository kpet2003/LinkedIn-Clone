package com.tediproject.tedi.model;

import java.util.Date;


public class Message {
    private long senderName;
    private long receiverName;
    private String message;
    private Date date;
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
