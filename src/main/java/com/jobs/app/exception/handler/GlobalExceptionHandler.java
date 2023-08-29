package com.jobs.app.exception.handler;

import com.jobs.app.enums.SqlConstraints;
import com.jobs.app.exception.JobApiException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JobApiException.class)
    ResponseEntity<Object> handleApiException(
        JobApiException exception
    ) {
        return new ResponseEntity<Object>(
            generateErrorResponse(exception.getErrorCode().codeValue(), exception.getMessage()),
            exception.getHttpStatus()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> handleConstraintViolationException(
        ConstraintViolationException exception
    ) {
        String constraintName = exception.getConstraintName();
        if (Objects.isNull(constraintName)) {
            String [] constraints = exception.getSQLException().getMessage().split("CONSTRAINT|FOREIGN KEY");
            constraintName = constraints[1].trim();
            constraintName = constraintName.substring(1, constraintName.length() - 1);
        }

        SqlConstraints constraint = findMatchingConstraint(constraintName);

        return new ResponseEntity<Object>(
            generateErrorResponse(constraint.code, constraint.message),
            HttpStatus.CONFLICT
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        String [] error = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage().split("#");
        return new ResponseEntity<Object>(
            generateErrorResponse(error[0], error[1]),
            HttpStatus.BAD_REQUEST
        );
    }

    private SqlConstraints findMatchingConstraint(String constraintName) {
        for (SqlConstraints value : SqlConstraints.values()) {
            if (value.constraint.contentEquals(constraintName)) {
                return value;
            }
        }
        return null;
    }

    private Map<String, Object> generateErrorResponse(
        String errorCode,
        String message
    ) {
        Map<String, Object> response = new HashMap();
        response.put("code", errorCode);
        response.put("message", message);

        return response;
    }

}
