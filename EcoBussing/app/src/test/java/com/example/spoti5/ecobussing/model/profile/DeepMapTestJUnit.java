package com.example.spoti5.ecobussing.model.profile;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This test calls tests the DeepMap class
 * Created by Hampus on 2015-10-30.
 */
public class DeepMapTestJUnit {
    private DeepMap testMap = new DeepMap();

    @Test
    public void testDeepMap(){
        testMap.setSpecificDate(2015, 07, 12, 3000.0);
        //Tests get and set specificDate
        assertTrue(testMap.getSpecificDate(2015, 07, 12) == 3000.0);
        //Tests addToSpecificDate
        testMap.addToSpecificDate(2015, 07, 12, 1200.0);
        assertTrue(testMap.getSpecificDate(2015, 07, 12) == 4200.0);
        //Tests addToCurrentDate
        testMap.addToCurrentDate(2000.0);
        assertTrue(2000.0 == testMap.getFromCurrentDate());
        //Tests getSumOfOneYear
        assertTrue(testMap.getSumOfOneYear(2015) == 6200);

        Double currentDateValue = testMap.getFromCurrentDate();
        testMap.addToCurrentDate(1000.0);
        assertTrue(currentDateValue + 1000.0 == testMap.getFromCurrentDate());
    }


}