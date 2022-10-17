package com.application.crs.utils;

import java.text.DateFormatSymbols;

public class ApplicationUtils {

    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}
