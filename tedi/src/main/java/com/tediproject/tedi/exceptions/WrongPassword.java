package com.tediproject.tedi.exceptions;

public class WrongPassword extends RuntimeException {
    public WrongPassword(String msg) {
        super(msg);
    }
}
