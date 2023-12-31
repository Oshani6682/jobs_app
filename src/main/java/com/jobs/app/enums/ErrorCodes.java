package com.jobs.app.enums;

public enum ErrorCodes {

    INVALID_USER_ROLE("UserRoleInvalid"),
    INVALID_USER_CREDENTIALS("UserCredentialsInvalid"),
    USER_NAME_NOT_UNIQUE("UserNameNotUnique"),
    COUNTRY_INVALID("CountryInvalid"),
    SECTOR_INVALID("SectorInvalid"),
    JOB_INVALID("JobInvalid"),
    CONSULTANT_NOT_FOUND("ConsultantNotFound"),
    JOB_SEEKER_NOT_FOUND("JobSeekerNotFound"),
    DAY_NOT_FOUND("DayNotFound"),
    TIME_INVALID("TimeInvalid"),
    DATE_INVALID("DateInvalid"),
    CONSULTANT_NOT_AVAILABLE("ConsultantNotAvailable"),
    APPOINTMENT_TIME_INVALID("AppointmentTimeInvalid");

    private final String code;
    ErrorCodes(String code) {
        this.code = code;
    }

    public String codeValue() {
        return code;
    }

}
