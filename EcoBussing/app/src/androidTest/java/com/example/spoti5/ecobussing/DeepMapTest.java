package com.example.spoti5.ecobussing;

import android.test.AndroidTestCase;

import com.example.spoti5.ecobussing.Profiles.DeepMap;

/**
 * Created by hilden on 2015-10-07.
 */
public class DeepMapTest extends AndroidTestCase {

    private DeepMap<Integer, Integer, Integer, Double> testMap;

    public DeepMapTest() {
        testMap = new DeepMap<>();
        test();
    }

    public void test() {

        /*
        testMap.setSpecificDate(2015, 1, 22, 1000.0);
        testMap.setSpecificDate(2015, 1, 3, 20.0);
        testMap.setSpecificDate(2016, 6, 28, 1.0);
*/
        System.out.println("TEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEST");

   /*
        System.out.println(Double.toString(testMap.getSpecificDate(2015, 1, 22)));
        System.out.println(Double.toString(testMap.getSpecificDate(2015, 1, 3)));
        System.out.println(Double.toString(testMap.getSpecificDate(2016, 6, 28)));
        System.out.println(testMap.getSpecificDate(2020, 6, 1));
        System.out.println(Double.toString(testMap.getSumOfAllDates()));

        testMap.addToSpecificDate(2016, 6, 28, 2.0);
        testMap.setSpecificDate(2015, 1, 22, 500.0);
        testMap.addToCurrentDate(20.0);
        testMap.addToCurrentDate(10.0);

        System.out.println("-----------------------------------------------------------------");

        System.out.println(Double.toString(testMap.getSpecificDate(2016, 6, 28)));
        System.out.println(Double.toString(testMap.getFromCurrentDate()));
        System.out.println(Double.toString(testMap.getSpecificDate(2015, 1, 22)));

*/
        System.out.println("-----------------------------------------------------------------");

        testMap.addToSpecificDate(2015, 10, 10, 1.0);
        testMap.addToSpecificDate(2015, 10, 9, 1.0);
        testMap.addToSpecificDate(2015, 10, 8, 1.0);
        testMap.addToSpecificDate(2015, 10, 7, 1.0);
        testMap.addToSpecificDate(2015, 10, 6, 1.0);
        testMap.addToSpecificDate(2015, 10, 6, 1.0);
        testMap.addToSpecificDate(2015, 10, 5, 1.0);
        testMap.addToSpecificDate(2015, 10, 4, 1.0);
        testMap.addToSpecificDate(2015, 10, 3, 20.0);
        testMap.addToSpecificDate(2015, 10, 2, 20.0);
        testMap.addToSpecificDate(2015, 10, 1, 20.0);

        testMap.addToSpecificDate(2014, 12, 1, 1.0);
        testMap.addToSpecificDate(2014, 11, 1, 1.0);
        testMap.addToSpecificDate(2014, 11, 1, 1.0);
        testMap.addToSpecificDate(2014, 11, 2, 1.0);
        testMap.addToSpecificDate(2013, 10, 9, 200.0);

        System.out.println(Double.toString(testMap.getSumOfPastSevenDays()));
        System.out.println(Double.toString(testMap.getSumOfOneYear(2014)));
        System.out.println(Double.toString(testMap.getSumOfOneMonth(2014, 11)));
        System.out.println(Double.toString(testMap.getSumOfOneYear(2015)));
    }
}