package com.example.spoti5.ecobussing.model.profile;

import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;

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
    }

    @Test
    public void getEmailShouldReturnTheUsersEmail(){
        assertTrue(testUser.getEmail().equals("test@mail.com"));
    }

    @Test
    public void getCompanyShouldReturnEmptyStringOnANewUser(){
        assertTrue(testUser.getCompany().equals(""));
    }

    @Test
    public void aUserShouldBeCreatedWith0DistanceAndCO2(){
        assertTrue(testUser.getCurrentDistance() == 0);
        assertTrue(testUser.getCO2Saved() == 0);
        assertTrue(testUser.getCo2Tot() == 0);
    }

    @Test
    public void newJourneyShouldIncCo2DistanceAndMoney(){
        double initCo2Saved = testUser.getCO2Saved();
        double initCo2Tot = testUser.getCo2Tot();
        double initDist = testUser.getDistanceTraveled();
        double initMoney = testUser.getMoneySaved();

        testUser.newJourney(2500);

        double laterCo2Saved = testUser.getCO2Saved();
        double laterCo2Tot = testUser.getCo2Tot();
        double laterDist = testUser.getDistanceTraveled();
        double laterMonet = testUser.getMoneySaved();

        assertTrue(initCo2Saved < laterCo2Saved);
        assertTrue(initCo2Saved == initCo2Tot);
        assertTrue(initDist < laterDist);
        assertTrue(initMoney < laterMonet);
        assertTrue(laterCo2Saved == laterCo2Tot);
        assertTrue( (laterDist-2500)> initDist-0.1 && (laterDist-2500)<initDist+0.1 );
    }

    @Test
    public void newJourneyShouldIncCurrentYearMonthWeek(){
        double initYear = testUser.getCo2CurrentYear();
        double initMonth = testUser.getCo2CurrentMonth();
        double initWeek = testUser.getCO2SavedPast7Days();

        testUser.newJourney(1200);

        double laterYear = testUser.getCo2CurrentYear();
        double laterMonth = testUser.getCo2CurrentMonth();
        double laterWeek = testUser.getCO2SavedPast7Days();

        assertTrue(initYear < laterYear);
        assertTrue(initMonth < laterMonth);
        assertTrue(initWeek < laterWeek);
    }

    @Test
    public void setNameShouldChangeNameOfUser(){
        testUser.setName("Mr Testing");
        assertTrue(testUser.getName().equals("Mr Testing"));
    }

    @Test
    public void testingMethodUpdateSpecDate(){
        double initCo2Saved = testUser.getCO2Saved();
        double initCo2Tot = testUser.getCo2Tot();
        double initCo21406 = testUser.getCO2SavedMonth(2015, 06);
        testUser.updateSpecDate(2500,2015,6,6);
        double laterCo2Saved = testUser.getCO2Saved();
        double laterCo2Tot = testUser.getCo2Tot();
        double laterCo21406 = testUser.getCO2SavedMonth(2015, 06);

        assertTrue(initCo21406 < laterCo21406);
        assertTrue(initCo2Tot == initCo2Saved);
        assertTrue(initCo2Tot < laterCo2Tot);
        assertTrue(laterCo2Tot == laterCo2Saved);
        assertTrue(initCo2Saved < laterCo2Saved);
    }

    @Test
    public void gettingCurrentMonthShouldNotReturnAnythingFromLongerAgo(){
        double initCo2Month = testUser.getCo2CurrentMonth();
        testUser.updateSpecDate(2500, 2015, 6, 6);
        double laterCo2Month = testUser.getCo2CurrentMonth();
        assertTrue(initCo2Month == laterCo2Month);
    }

    @Test
    public void setCompanyShouldChangeAUsersCompany(){
        testUser.setCompany("Test");
        assertTrue(testUser.getCompany().equals("Test"));
    }

    @Test
    public void aJourneyShouldIncMoneySaved(){
        double initMoneyWeek = testUser.getMoneySavedPast7Days();
        double initMoney = testUser.getMoneySaved();

        testUser.newJourney(5200);

        assertTrue(initMoneyWeek < testUser.getMoneySavedPast7Days());
        assertTrue(initMoney < testUser.getMoneySaved());
    }

    @Test
    public void getTotalTimesTraveledShouldReturn1MoreAfterAJourney(){
        int initTimes = testUser.getTotaltTimesTraveled();

        testUser.newJourney(2000);

        assertTrue(initTimes == (testUser.getTotaltTimesTraveled() - 1));

        testUser.updateSpecDate(2500, 2015, 6, 6);

        assertTrue(initTimes == (testUser.getTotaltTimesTraveled() - 2));

        testUser.updateSpecDate(2500, 2015, 6, 8);
        testUser.updateSpecDate(2500, 2015, 5, 6);

        assertTrue(initTimes == (testUser.getTotaltTimesTraveled() - 4));
    }

}