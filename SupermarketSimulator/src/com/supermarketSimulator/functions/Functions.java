package com.supermarketSimulator.functions;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Functions {
    public static int randomNumber(int limit) {
        return new Random().nextInt(limit);
    }

    public static Date addRandomTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, randomNumber(30));
        return cal.getTime();
    }

    public static Date addHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

}
