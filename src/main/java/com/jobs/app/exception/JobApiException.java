package com.jobs.app.exception;

import com.jobs.app.enums.ErrorCodes;
import org.springframework.http.HttpStatus;

public class JobApiException extends RuntimeException {
    private final String message;
    private final ErrorCodes errorCode;
    private final HttpStatus httpStatus;

    protected JobApiException(
        String message,
        ErrorCodes errorCode,
        HttpStatus httpStatus
    ) {
        super(message);

        this.message = message;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
