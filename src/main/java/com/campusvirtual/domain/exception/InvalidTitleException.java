package com.campusvirtual.domain.exception;

public class InvalidTitleException extends RuntimeException {
    public InvalidTitleException(String message) {
        super(message);
    }
}