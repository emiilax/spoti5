package com.example.spoti5.ecobussing;

import android.test.AndroidTestCase;

import com.example.spoti5.ecobussing.Profiles.TimeStampedDouble;

/**
 * Created by hilden on 2015-10-06.
 */
public class TimeStampedDoubleTest extends AndroidTestCase {

    private TimeStampedDouble distance;

    public TimeStampedDoubleTest() {
        distance = new TimeStampedDouble(20.0);
        Test1();
    }

    private void Test1() {
        System.out.println("-------------------------------- TIMESTAMPEDDOUBLE TEST -------------------------------");
        System.out.println("This should match the current date:");
        System.out.println("YEAR: " + distance.getStampedYear());
        System.out.println("MONTH: " + distance.getStampedMonth());
        System.out.println("DAY: " + distance.getStampedDay());
        System.out.println(distance.getStampedYear() + "/" + distance.getStampedMonth() + "/" + distance.getStampedDay());
    }
}
