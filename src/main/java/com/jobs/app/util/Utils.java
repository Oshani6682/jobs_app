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

    public static int convertTimeToIntValue(String timeCode) {
        return Integer.parseInt(timeCode.replace(":", ""));
    }

    public static String convertTimeIntValueToTime(Integer timeInInt) {
        String timeInString = timeInInt.toString();
        return timeInString.substring(0, 2) + ":" + timeInString.substring(2, 4);
    }

}
