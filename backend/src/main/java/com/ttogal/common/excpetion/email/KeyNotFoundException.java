package com.ttogal.common.excpetion.email;

public class KeyNotFoundException extends RuntimeException{
    public KeyNotFoundException() {
    }

    public KeyNotFoundException(String message) {
        super(message);
    }
}
