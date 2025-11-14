package ru.meetpoint.eventservice.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ServiceException {

    public NotFoundException(String message) {
        super(HttpStatus.valueOf(404), message);
    }
}
