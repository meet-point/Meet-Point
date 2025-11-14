package ru.meetpoint.authservice.error.exception;

import org.springframework.http.HttpStatus;
import ru.meetpoint.security.starter.data.enums.ErrorCode;

public class NotFoundException extends ServiceException {

    public NotFoundException(ErrorCode errorCode, String message) {
        super(HttpStatus.valueOf(404), errorCode, message);
    }
}
