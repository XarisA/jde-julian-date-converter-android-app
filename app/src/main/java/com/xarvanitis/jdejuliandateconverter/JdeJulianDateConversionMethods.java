package com.xarvanitis.jdejuliandateconverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * JD Edwards Julian Date Conversion Methods in Java.
 * This class contains methods for Date conversions between
 * JD Edwards Julian Dates and Calendar Dates
 * Requires Java Version 8
 *
 * The JDE Julian Date format specification can be found at the following link
 * https://docs.oracle.com/cd/E26228_01/doc.93/e21961/julian_date_conv.htm
 */
public class JdeJulianDateConversionMethods {

    /**
     * CalendarToJulian: converts a Calendar to the JDEdwards Julian date format.
     */
    public static String CalendarToJulian(LocalDate ld){
        int year  = ld.getYear();
        int dayOfYear   = ld.getDayOfYear();
        int centuryFrom1900 = ( ld.getYear () / 100 ) - 19;
        StringBuilder sb = new StringBuilder();
        return String.valueOf(sb.append(centuryFrom1900).append(Integer.toString(year).substring(2, 4))
                .append(String.format("%03d",dayOfYear)));
    }

    /**
     * JulianToCalendar: converts a JDEdwards Julian date to a Calendar Date.
     */
    public static LocalDate JulianToCalendar(String julianDate) {
        LocalDate ld;
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 2,
                        1900+100*Character.getNumericValue(julianDate.charAt(0)))
                .appendPattern("D")
                .toFormatter();
        ld = LocalDate.parse(julianDate.substring(1), formatter);
        return ld;
    }

    /**
     * FirstDayPreviousMonthJulian: returns the first day of the previous month
     * in JDEdwards Julian date format.
     */
    public static String FirstDayPreviousMonthJulian() {
        LocalDate localDate =LocalDate.now().minusMonths(1).withDayOfMonth(1);
        return CalendarToJulian(localDate);
    }

    /**
     * LastDayPreviousMonthJulian: returns the last day of the previous month
     * in JDEdwards Julian date format.
     */
    public static String LastDayPreviousMonthJulian() {
        LocalDate localDate =LocalDate.now().minusMonths(1);
        localDate= localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()));
        return CalendarToJulian(localDate);
    }

    /**
     * FirstDayCurrentMonthJulian: returns the first day of the current month
     * in JDEdwards Julian date format.
     */
    public static String FirstDayCurrentMonthJulian() {
        LocalDate localDate =LocalDate.now().withDayOfMonth(1);
        return CalendarToJulian(localDate);
    }

    /**
     * LastDayCurrentMonthJulian: returns the last day of the current month
     * in JDEdwards Julian date format.
     */
    public static String LastDayCurrentMonthJulian() {
        LocalDate localDate =LocalDate.now();
        localDate = localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()));
        return CalendarToJulian(localDate);
    }

    /**
     * FirstDayOfCurrentYear: returns the first day of the current year
     * in JDEdwards Julian date format.
     */
    public static String FirstDayOfCurrentYear() {
        LocalDate today =LocalDate.now().withDayOfMonth(1).withMonth(1);
        return CalendarToJulian(today);
    }
}
