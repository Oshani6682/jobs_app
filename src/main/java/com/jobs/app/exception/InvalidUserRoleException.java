package com.jobs.app.exception;

import com.jobs.app.enums.ErrorCodes;
import org.springframework.http.HttpStatus;

public class InvalidUserRoleException extends JobApiException {

    public InvalidUserRoleException(String role) {
        super(
            "Invalid user role '" + role + "'",
            ErrorCodes.INVALID_USER_ROLE,
            HttpStatus.BAD_REQUEST
        );
    }

}
