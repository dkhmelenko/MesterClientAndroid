package com.khmelenko.lab.mester.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provides utilities for the work with strings
 *
 * @author Dmytro Khmelenko
 */
public final class StringUtils {

    private StringUtils() {
    }

    /**
     * Parses date from the string
     *
     * @param dateString String with date
     * @return Parsed date
     */
    public static Date parseDate(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        Date date = new Date();
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
