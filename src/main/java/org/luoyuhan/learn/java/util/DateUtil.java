package org.luoyuhan.learn.java.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(localDateTime.format(dateTimeFormatter));
        System.out.println(dateToLocalDate(new Date()));
        System.out.println(dateToLocalDateTime(new Date()));
        System.out.println(dateToLocalDateTimeFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String dateToLocalDateTimeFormat(Date date, String formatter) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return localDateTime.format(DateTimeFormatter.ofPattern(formatter));
    }
}
