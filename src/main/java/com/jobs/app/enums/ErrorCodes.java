package com.jobs.app.enums;

public enum ErrorCodes {
    INVALID_USER_ROLE("InvalidUserRole");

    private final String code;
    ErrorCodes(String code) {
        this.code = code;
    }

    public String codeValue() {
        return code;
    }

}
