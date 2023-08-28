package com.jobs.app.enums;

public enum ErrorCodes {

    INVALID_USER_ROLE("UserRoleInvalid"),
    INVALID_USER_CREDENTIALS("UserCredentialsInvalid"),
    USER_NAME_NOT_UNIQUE("UserNameNotUnique"),
    COUNTRY_INVALID("CountryInvalid"),
    SECTOR_INVALID("SectorInvalid");

    private final String code;
    ErrorCodes(String code) {
        this.code = code;
    }

    public String codeValue() {
        return code;
    }

}
