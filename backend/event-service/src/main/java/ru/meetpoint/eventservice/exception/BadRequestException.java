package ru.meetpoint.eventservice.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ServiceException {

    public BadRequestException(String message) {
        super(HttpStatus.valueOf(400), message);
    }
}
