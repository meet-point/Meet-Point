package ru.meetpoint.authservice.error.exception;

import org.springframework.http.HttpStatus;
import ru.meetpoint.authservice.error.enums.ErrorCode;

public class BadRequestException extends ServiceException {

    public BadRequestException(ErrorCode errorCode, String message) {
        super(HttpStatus.valueOf(400), errorCode, message);
    }
}
