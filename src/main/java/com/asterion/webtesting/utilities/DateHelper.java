package com.asterion.webtesting.utilities;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public final class DateHelper {

    public static final long SECONDSPERDAY = 86400L;
    public static final long SECONDSPERHOUR = 3600L;
    public static final long SECONDSPERMINUTE = 60L;
    public static final long MILLISECONDSPERDAY = SECONDSPERDAY * 1000;
    public static final long MILLISECONDSPERHOUR = SECONDSPERHOUR * 1000;
    public static final long MILLISECONDSPERMINUTE = SECONDSPERMINUTE * 1000;
    public static final String defaultLocalDatePattern = "dd-MM-yyyy";
    public static final String defaultLocalDateTimePattern = "dd-MM-yyyy HH:mm";
    public static final Timezone defaultTimeZone = Timezone.BRUSSELS;

    public static long getUnixSecondsTime() {
        return Instant.now().getEpochSecond();
    }

    public static long getUnixMillisecondsTime() {
        return Instant.now().getEpochSecond() * 1000;
    }

    public static LocalDate getLocalDateNow() {
        return getLocalDateNow(defaultTimeZone);
    }

    public static LocalDateTime getLocalDateTimeNow() {
        return getLocalDateTimeNow(defaultTimeZone);
    }

    public static LocalDate getLocalDateNow(Timezone timezone) {
        ZoneId zoneId = timezone.getZoneId();
        return LocalDate.now(zoneId);
    }

    public static LocalDate getLocalDate(String localDateString, String pattern){
        return (LocalDate) convertExceptions(() -> LocalDate.parse(localDateString, DateTimeFormatter.ofPattern(pattern)));
    }

    public static LocalDate getLocalDate(String localDateString, DateTimeFormatter dateTimeFormatter){
        return (LocalDate) convertExceptions(() -> LocalDate.parse(localDateString, dateTimeFormatter));
    }

    public static LocalDate getLocalDate(String dayString, String monthString, String yearString) {
        try{
            int day = Integer.parseInt(dayString);
            int month = Integer.parseInt(monthString);
            int year = Integer.parseInt(yearString);
            return LocalDate.of(year, month, day);
        } catch(NumberFormatException e){
            throw new IllegalArgumentException("Could not format date of strings: " + dayString + "," + monthString + "," + yearString);
        }
    }

    public static LocalDateTime getStartOfDayLocalDateTime(String dayString, String monthString, String yearString) {
        return getStartOfDayLocalDateTime(dayString, monthString, yearString, defaultTimeZone);
    }

    public static LocalDateTime getStartOfDayLocalDateTime(String dayString, String monthString, String yearString, Timezone timezone) {
        try{
            int day = Integer.parseInt(dayString);
            int month = Integer.parseInt(monthString);
            int year = Integer.parseInt(yearString);
            return LocalDate.of(year, month, day).atStartOfDay(timezone.getZoneId()).toLocalDateTime();
        } catch(NumberFormatException e){
            throw new IllegalArgumentException("Could not format date of strings: " + dayString + "," + monthString + "," + yearString);
        }
    }

    public static LocalDateTime getStartOfDayLocalDateTime(String dateString, String pattern) {
        return getStartOfDayLocalDateTime(dateString, pattern, defaultTimeZone);
    }

    public static LocalDateTime getStartOfDayLocalDateTime(String dateString, String pattern, Timezone timezone) {
        return getLocalDate(dateString, pattern).atStartOfDay(timezone.getZoneId()).toLocalDateTime();
    }

    public static LocalDateTime getLocalDateTimeNow(Timezone timezone) {
        ZoneId zoneId = timezone.getZoneId();
        return LocalDateTime.now(zoneId);
    }

    public static LocalDateTime getLocalDateTime(String localDateTimeString, String pattern){
        return (LocalDateTime) convertExceptions(() -> LocalDateTime.parse(localDateTimeString, DateTimeFormatter.ofPattern(pattern)));
    }

    public static LocalDateTime getLocalDateTime(String localDateTimeString, DateTimeFormatter dateTimeFormatter){
        return (LocalDateTime) convertExceptions(() -> LocalDateTime.parse(localDateTimeString, dateTimeFormatter));
    }

    public static String getLocalDateAsString(LocalDate localDate, String pattern) {
        return (String) convertExceptions(() -> DateTimeFormatter.ofPattern(pattern).format(localDate));
    }

    public static String getLocalDateAsString(LocalDate localDate) {
        return (String) convertExceptions(() -> DateTimeFormatter.ofPattern(defaultLocalDatePattern).format(localDate));
    }

    public static String getLocalDateTimeAsString(LocalDateTime localDateTime, String pattern) {
        return (String) convertExceptions(() -> DateTimeFormatter.ofPattern(pattern).format(localDateTime));
    }

    public static String getLocalDateTimeAsString(LocalDateTime localDateTime) {
        return (String) convertExceptions(() -> DateTimeFormatter.ofPattern(defaultLocalDateTimePattern).format(localDateTime));
    }

    public static LocalDate getLocalDateFromUnixSeconds(long unixSeconds, Timezone timezone) {
        return getLocalDateTimeFromUnixSeconds(unixSeconds, timezone).toLocalDate();
    }

    public static LocalDate getLocalDateFromUnixMillisseconds(long unixMilliseconds, Timezone timezone) {
        long unixSeconds = unixMilliseconds / 1000;
        return getLocalDateTimeFromUnixSeconds(unixSeconds, timezone).toLocalDate();
    }

    public static LocalDateTime getLocalDateTimeFromUnixSeconds(long unixSeconds, Timezone timezone) {
        return (LocalDateTime) convertExceptions(() -> LocalDateTime.ofInstant(Instant.ofEpochSecond(unixSeconds), timezone.getZoneId()));
    }

    public static LocalDateTime getLocalDateTimeFromUnixMilliseconds(long unixMilliseconds, Timezone timezone) {
        long unixSeconds = unixMilliseconds / 1000;
        return (LocalDateTime) convertExceptions(() ->  LocalDateTime.ofInstant(Instant.ofEpochSecond(unixSeconds), timezone.getZoneId()));
    }

    public static long addDaysToUnixSeconds(long unixSeconds, int days) {
        return unixSeconds + (days * SECONDSPERDAY);
    }

    public static long addHoursToUnixSeconds(long unixSeconds, int hours) {
        return unixSeconds + (hours * SECONDSPERHOUR);
    }

    public static long addMinutesToUnixSeconds(long unixSeconds, int minutes) {
        return unixSeconds + (minutes * SECONDSPERMINUTE);
    }

    public static long addDaysToUnixMilliseconds(long unixMilliseconds, int days) {
        return unixMilliseconds + (days * MILLISECONDSPERDAY);
    }

    public static long addHoursToUnixMilliseconds(long unixMilliseconds, int hours) {
        return unixMilliseconds + (hours * MILLISECONDSPERHOUR);
    }

    public static long addMinutesToUnixMilliseconds(long unixMilliseconds, int minutes) {
        return unixMilliseconds + (minutes * MILLISECONDSPERMINUTE);
    }

    public static Long getUnixSecondsBeginningOfDay(LocalDate localDate, Timezone timezone) {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(timezone.getZoneId());
        return zonedDateTime.toInstant().getEpochSecond();
    }

    public static long getUnixSecondsEndOfDay(LocalDate localDate, Timezone timezone) {
        LocalDateTime localDateTime = localDate.atTime(23, 59, 59);
        ZonedDateTime zonedDateTime = localDateTime.atZone(timezone.getZoneId());
        return zonedDateTime.toInstant().getEpochSecond();
    }

    public static Long getUnixSeconds(LocalDateTime localDateTime, Timezone timezone) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(timezone.getZoneId());
        return zonedDateTime.toInstant().getEpochSecond();
    }

    public static long getUnixMillisecondsBeginningOfDay(LocalDate localDate, Timezone timezone) {
        return getUnixSecondsBeginningOfDay(localDate, timezone) *1000;
    }

    public static long getUnixMillisecondsEndOfDay(LocalDate localDate, Timezone timezone) {
        return getUnixSecondsEndOfDay(localDate, timezone) *1000;
    }

    public static long getUnixMilliSeconds(LocalDateTime localDateTime, Timezone timezone) {
        return getUnixSeconds(localDateTime, timezone) * 1000;
    }

    public static int getNumberOfDaysBetween(LocalDate start, LocalDate end) {
        return (int) ChronoUnit.DAYS.between(start, end);
    }

    public static String getYearString(LocalDate localDate) {
        return String.valueOf(localDate.getYear());
    }

    public static String getMonthString(LocalDate localDate) {
        int month = localDate.getMonthValue();
        if (month < 10) {
            return "0" + month;
        } else {
            return String.valueOf(month);
        }
    }

    public static String getDayOfMonthString(LocalDate localDate) {
        int dayOfMonth = localDate.getDayOfMonth();
        if (dayOfMonth < 10) {
            return "0" + dayOfMonth;
        } else {
            return String.valueOf(dayOfMonth);
        }
    }

    public static LocalDate getLastDayOfMonth(int year, int month){
        LocalDate date = LocalDate.of(year, month, 1);
        while(date.getMonthValue() == month){
            date = date.plusDays(1);
        } return date.minusDays(1);
    }

    public static Date asUtilDate(LocalDate localDate) {
        return asUtilDate(localDate, defaultTimeZone);
    }
    public static Date asUtilDate(LocalDate localDate, Timezone timezone) {
        return Date.from(localDate.atStartOfDay().atZone(timezone.getZoneId()).toInstant());
    }

    public static Date asUtilDate(LocalDateTime localDateTime) {
        return asUtilDate(localDateTime, defaultTimeZone);
    }

    public static Date asUtilDate(LocalDateTime localDateTime, Timezone timezone) {
        return Date.from(localDateTime.atZone(timezone.getZoneId()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return asLocalDate(date, defaultTimeZone);
    }

    public static LocalDate asLocalDate(Date date, Timezone timezone) {
        return Instant.ofEpochMilli(date.getTime()).atZone(timezone.getZoneId()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return asLocalDateTime(date, defaultTimeZone);
    }

    public static LocalDateTime asLocalDateTime(Date date, Timezone timezone) {
        return Instant.ofEpochMilli(date.getTime()).atZone(timezone.getZoneId()).toLocalDateTime();
    }


    public enum Timezone {

        ATHENS("Europe/Athens"),
        BERLIN("Europe/Berlin"),
        BRUSSELS("Europe/Brussels"),
        DUBLIN("Europe/Dublin"),
        GMT("Greenwich"),
        HONGKONG("Asia/Hong_Kong"),
        LONDON("Europe/London"),
        LOS_ANGELES("America/Los_Angeles"),
        MOSCOW("Europe/Moscow"),
        NEW_YORK("America/New_York"),
        SYDNEY("Australia/Sydney"),
        UTC("UTC");

        private final String representation;

        Timezone(String id) {
            this.representation = id;
        }

        private ZoneId getZoneId() {
            return ZoneId.of(representation);
        }
    }

    public static Object convertExceptions(DateTimeTask t)  {
        try {
            return t.run();
        } catch (DateTimeException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Exception thrown while parsing date: " + e.toString());
        }
    }

    public interface DateTimeTask {
        Object run();
    }
}