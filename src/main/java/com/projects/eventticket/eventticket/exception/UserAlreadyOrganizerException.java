package com.projects.eventticket.eventticket.exception;

public class UserAlreadyOrganizerException extends RuntimeException {
    public UserAlreadyOrganizerException() {
    }

    public UserAlreadyOrganizerException(String message) {
        super(message);
    }
}
