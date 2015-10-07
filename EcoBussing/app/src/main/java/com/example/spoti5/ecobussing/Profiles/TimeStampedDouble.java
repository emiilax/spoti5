package com.example.spoti5.ecobussing.Profiles;

import java.util.Calendar;

/**
 * A class to keep a double but that also keeps track of when the double was created.
 * Needed to be able to split distances users has traveled into different categories
 * such as "distance traveled in November".
 *
 * Created by hilden on 2015-10-04.
 */
public class TimeStampedDouble {

    private double value;
    private long stampedDateInMillis;

    private int stampedYear;
    private int stampedMonth;
    private int stampedDay;

    private Calendar calendar = Calendar.getInstance();

    public TimeStampedDouble(Double value) {
        this.value = value;

        stampedDateInMillis = calendar.getTimeInMillis();

        calendar.setTimeInMillis(stampedDateInMillis);
        stampedYear = calendar.get(Calendar.YEAR);
        stampedMonth = calendar.get(Calendar.MONTH) + 1; //The month variable Calendar. Month starts from 0,
                                                         // so +1 is needed to show the "correct" month.
        stampedDay = calendar.get(Calendar.DAY_OF_MONTH);


    }

    public double getValue() {
        return value;
    }

    public long getStampedDateInMillis() {
        return stampedDateInMillis;
    }

    public int getStampedYear() {
        return stampedYear;
    }

    public int getStampedMonth() {
        return stampedMonth;
    }

    public int getStampedDay() {
        return stampedDay;
    }
}
