package ru.meetpoint.authservice.error.exception;

import org.springframework.http.HttpStatus;
import ru.meetpoint.authservice.error.enums.ErrorCode;

public class InternalException extends ServiceException {

    public InternalException(ErrorCode errorCode, String message) {
        super(HttpStatus.valueOf(500), errorCode, message);
    }
}
