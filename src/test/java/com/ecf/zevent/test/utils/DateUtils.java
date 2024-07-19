package com.ecf.zevent.test.utils;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DateUtils {
    public static int AGE_MIN = 13;
    public static int AGE_MAX = 80;

    public static LocalDate dateAgeMax() { return getDateAgo(AGE_MAX);}
    public static LocalDate dateAgeMin() { return getDateAgo(AGE_MIN);}

    public static LocalDate getDateAgo( int age) {
        return LocalDate.now().minusYears(age);
    }

    public static LocalDate dateBetween(LocalDate dateStart, LocalDate dateEnd) {
        long randomDay = ThreadLocalRandom.current().nextLong(
                dateStart.toEpochDay(), dateEnd.toEpochDay()
        );
        return LocalDate.ofEpochDay(randomDay);
    }

    public static LocalDate randomBirthDate() {
        return dateBetween(dateAgeMax(), dateAgeMin());
    }

}
