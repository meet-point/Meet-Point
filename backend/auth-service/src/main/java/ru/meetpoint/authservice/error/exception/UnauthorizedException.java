package ru.meetpoint.authservice.error.exception;

import org.springframework.http.HttpStatus;
import ru.meetpoint.authservice.error.enums.ErrorCode;

public class UnauthorizedException extends ServiceException {

    public UnauthorizedException(ErrorCode errorCode, String message) {
        super( HttpStatus.valueOf(401), errorCode, message);
    }
}
