package com.jobs.app.exception.handler;

import com.jobs.app.exception.JobApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JobApiException.class)
    private ResponseEntity<Object> handleApiException(
        JobApiException exception
    ) {
        Map<String, Object> response = new HashMap();
        response.put("code", exception.getErrorCode().codeValue());
        response.put("message", exception.getMessage());

        return new ResponseEntity<Object>(
            response,
            exception.getHttpStatus()
        );
    }

}
