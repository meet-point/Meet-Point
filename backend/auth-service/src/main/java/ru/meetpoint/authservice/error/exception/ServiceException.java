package ru.meetpoint.authservice.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.meetpoint.security.starter.data.enums.ErrorCode;

@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final ErrorCode errorCode;

    public ServiceException(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return String.format("ServiceException: status=%s, code=%s, message=%s", httpStatus, errorCode, getMessage());
    }
}
