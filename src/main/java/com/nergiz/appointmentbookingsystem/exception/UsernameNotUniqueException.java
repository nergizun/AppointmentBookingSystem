package com.nergiz.appointmentbookingsystem.exception;

public class UsernameNotUniqueException extends RuntimeException {

    public UsernameNotUniqueException(String username) {
        super("Username '" + username + "' is not unique.");
    }
}
