package ru.meetpoint.authservice.error.exception;

import org.springframework.http.HttpStatus;
import ru.meetpoint.security.starter.data.enums.ErrorCode;

public class ForbiddenException extends ServiceException {

    public ForbiddenException(ErrorCode errorCode, String message) {
        super(HttpStatus.valueOf(403), errorCode, message);
    }
}
