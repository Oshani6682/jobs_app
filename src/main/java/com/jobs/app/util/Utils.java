package com.jobs.app.util;

import com.jobs.app.domain.User;
import com.jobs.app.enums.UserRole;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Utils() {}

    public static String capitalizeEachLetter(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String subString : text.toLowerCase().split(" ")) {
            stringBuilder.append(StringUtils.capitalize(subString))
                .append(" ");
        }
        return stringBuilder.toString().trim();
    }

    public static UserRole findMatchingUserRole(String userRole) {
        for (UserRole value : UserRole.values()) {
            if (value.toString().contentEquals(userRole)) {
                return value;
            }
        }
        return null;
    }

    public static Date convertStringToDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException exception) {
            return null;
        }
    }

    public static String getDisplayName(User user) {
        return user.firstName + " " + user.lastName;
    }

    public static BigInteger getDateIntCode(Date date) {
        return convertDateToBigIntValue(dateFormat.format(date));
    }

    public static int convertTimeToIntValue(String timeCode) {
        return Integer.parseInt(timeCode.replace(":", ""));
    }

    public static String convertTimeIntValueToTime(Integer timeInInt) {
        String timeInString = timeInInt.toString();
        if (timeInString.length() == 3) {
            return "0" + timeInString.charAt(0) + ":" + timeInString.substring(1, 3);
        }
        return timeInString.substring(0, 2) + ":" + timeInString.substring(2, 4);
    }

    public static BigInteger convertDateToBigIntValue(String dateCode) {
        return BigInteger.valueOf(
            Long.parseLong(dateCode.replaceAll("-", ""))
        );
    }

    public static String convertDateBigIntValueToDate(BigInteger dateInInt) {
        String dateInString = dateInInt.toString();
        return dateInString.substring(0, 4) +
                "-" + dateInString.substring(4, 6) +
                "-" + dateInString.substring(6, 8);
    }

}
