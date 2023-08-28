package com.jobs.app.enums;

public enum UserRole {

    MANAGER("manager"),
    ADMIN("admin"),
    JOB_SEEKER("jobSeeker"),
    CONSULTANT("consultant");

    private final String role;
    UserRole(String userRole) {
        this.role = userRole;
    }

    public String toString() {
        return role;
    }

}
