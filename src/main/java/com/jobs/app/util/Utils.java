package com.jobs.app.util;

import com.jobs.app.enums.UserRole;

public class Utils {

    private Utils() {}

    public static UserRole findMatchingUserRole(String userRole) {
        for (UserRole value : UserRole.values()) {
            if (value.toString().contentEquals(userRole)) {
                return value;
            }
        }
        return null;
    }

}
