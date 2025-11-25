package ru.meetpoint.eventservice.error.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ServiceException {
    public ForbiddenException(String message) {
        super(HttpStatus.valueOf(403), message);
    }

}
