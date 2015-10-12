package com.example.spoti5.ecobussing;

import android.test.AndroidTestCase;

import com.example.spoti5.ecobussing.Profiles.User;

/**
 * Created by hilden on 2015-10-09.
 */
public class UserTest extends AndroidTestCase {

    private User testUser;

    public UserTest() {
        testUser = new User("test", "abc");
    }

    public void test() {
        testUser.incCO2Saved(1.0);
        testUser.incCO2Saved(1.0);
        System.out.println("-------------------------- TEST ------------------------------");
        System.out.println(Double.toString(testUser.getCO2Saved(true)));
        System.out.println(Double.toString(testUser.getCo2Tot()));
        System.out.println(Double.toString(testUser.getCo2CurrentYear()));
        System.out.println(Double.toString(testUser.getCo2CurrentMonth()));
    }
}
