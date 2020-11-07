package com.cupshe.ak.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

/**
 * DateTimeUtils
 *
 * @author zxy
 */
public class DateTimeUtils {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String STANDARD_FORMAT = "MM/dd/yyyy";

    public static final String DEFAULT_TIME_ZONE = "GMT+01:00";

    public static String now() {
        return nowOfTimeZone(TimeZone.getDefault());
    }

    public static String now(String pattern) {
        return format(new Date(), pattern);
    }

    public static String nowOfGmt() {
        return nowOfTimeZone(DEFAULT_TIME_ZONE);
    }

    public static String nowOfTimeZone(String timeZone) {
        return nowOfTimeZone(TimeZone.getTimeZone(timeZone));
    }

    public static String nowOfTimeZone(TimeZone timeZone) {
        return format(new Date(), DEFAULT_FORMAT, timeZone);
    }

    public static String format(Date date) {
        return format(date, DEFAULT_FORMAT);
    }

    public static String format(Date date, String pattern) {
        return format(date, pattern, TimeZone.getDefault());
    }

    public static String format(Date date, String pattern, String timeZone) {
        return format(date, pattern, TimeZone.getTimeZone(timeZone));
    }

    public static String format(Date date, String pattern, TimeZone timeZone) {
        return date == null ? null : getDateFormat(pattern, timeZone).format(date);
    }

    public static Date parse(String date) {
        return parse(date, DEFAULT_FORMAT);
    }

    public static Date parse(String date, String pattern) {
        return parse(date, pattern, TimeZone.getDefault());
    }

    public static Date parse(String date, String pattern, String timeZone) {
        return parse(date, pattern, TimeZone.getTimeZone(timeZone));
    }

    public static Date parse(String date, String pattern, TimeZone timeZone) {
        if (date == null) {
            return null;
        }

        try {
            return getDateFormat(pattern, timeZone).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String convert(String date, String pattern) {
        return format(parse(date, DEFAULT_FORMAT, DEFAULT_TIME_ZONE), pattern);
    }

    public static String convert(String date, String pattern, String timeZone) {
        return format(parse(date, DEFAULT_FORMAT, DEFAULT_TIME_ZONE), pattern, timeZone);
    }

    public static String convert(String date, String pattern, TimeZone timeZone) {
        return format(parse(date, DEFAULT_FORMAT, DEFAULT_TIME_ZONE), pattern, timeZone);
    }

    public static int diffDay(Date now, Date tar) {
        if (now == null || tar == null) {
            return -1;
        }

        double diff = (double) now.getTime() - tar.getTime();
        return (int) Math.floor(diff / (1000 * 60 * 60 * 24));
    }

    public static int diffDay(String now, String tar) {
        Date t1 = parse(now, DEFAULT_FORMAT, DEFAULT_TIME_ZONE);
        Date t2 = parse(tar, DEFAULT_FORMAT, DEFAULT_TIME_ZONE);
        return diffDay(t1, t2);
    }

    private static SimpleDateFormat getDateFormat(String pattern, TimeZone timeZone) {
        SimpleDateFormat result = new SimpleDateFormat(pattern);
        result.setTimeZone(timeZone);
        return result;
    }
    
    //LocalDate -> Date
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    //LocalDateTime -> Date
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    //Date -> LocalDate
    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    //Date -> LocalDateTime
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
