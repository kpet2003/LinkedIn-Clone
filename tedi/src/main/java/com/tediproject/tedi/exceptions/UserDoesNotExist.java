package com.tediproject.tedi.exceptions;

public class UserDoesNotExist extends RuntimeException {
    public UserDoesNotExist(String msg) {
        super(msg);
    }
}
