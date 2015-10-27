package com.example.spoti5.ecobussing.model.profile;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hampus on 2015-10-27.
 */
public class UserTestJUnit {
    private User testUser = new User("test@mail.com", "Mr Test");


    @Test
    public void getNameShouldReturnTheNameSetEarlier(){
        assertTrue(testUser.getName().equals("Mr Test"));
        assertFalse(testUser.getName().equals(""));
    }

    @Test
    public void aUserShouldBeCreatedWith0DistanceAndCO2(){
        assertTrue(testUser.getCurrentDistance() == 0);
        assertTrue(testUser.getCO2Saved() == 0);
    }

    @Test
    public void incAUsersCO2ShouldIncBothCO2AndDistance(){
        double initCo2 = testUser.getCO2Saved();
        double initDist = testUser.getDistanceTraveled();
        testUser.incCO2Saved(2500);
        double laterCo2 = testUser.getCO2Saved();
        double laterDist = testUser.getDistanceTraveled();
        assertTrue(initCo2<laterCo2);
        assertTrue(initDist<laterDist);
    }
}