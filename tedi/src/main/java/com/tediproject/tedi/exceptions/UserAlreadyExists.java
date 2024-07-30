package com.tediproject.tedi.exceptions;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String msg) {
        super(msg);
    }
}
