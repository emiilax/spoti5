package com.example.spoti5.ecobussing.Profiles;

import java.util.Calendar;

/**
 * Created by hilden on 2015-10-04.
 */
public class TimeStampedDouble {

    private double value;
    private long currentDateInMillis;
    private Calendar calendar = Calendar.getInstance();

    public TimeStampedDouble(Double value) {
        this.value = value;
        currentDateInMillis = calendar.getTimeInMillis();
    }
}
