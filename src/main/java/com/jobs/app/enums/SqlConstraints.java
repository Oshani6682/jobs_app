package com.jobs.app.enums;

public enum SqlConstraints {

    USER_NAME_UNIQUE("user.user_name_unique_idx", "UserNameNotUnique", "User name already exists");

    public final String constraint;
    public final String code;
    public final String message;

    SqlConstraints(String constraint, String code, String message) {
        this.constraint = constraint;
        this.code = code;
        this.message = message;
    }

}
