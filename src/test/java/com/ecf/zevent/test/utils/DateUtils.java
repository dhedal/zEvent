package com.ecf.zevent.test.utils;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DateUtils {
    public static int AGE_MIN = 13;
    public static int AGE_MAX = 80;

    public static LocalDate DATE_YEAR_AGO_MAX = LocalDate.of(getYearAgo(AGE_MAX), Month.JANUARY, 1);
    public static LocalDate DATE_YEAR_AGO_MIN = LocalDate.of(getYearAgo(AGE_MIN), Month.JANUARY, 1);;

    public static int getYearAgo( int age) {
        return LocalDate.now().minusYears(age).getYear();
    }

    public static LocalDate dateBetween(LocalDate dateStart, LocalDate dateEnd) {
        long randomDay = ThreadLocalRandom.current().nextLong(
                dateStart.toEpochDay(), dateEnd.toEpochDay()
        );
        return LocalDate.ofEpochDay(randomDay);
    }

    public static LocalDate randomBirthDate() {
        return dateBetween(DATE_YEAR_AGO_MAX, DATE_YEAR_AGO_MIN);
    }

}
