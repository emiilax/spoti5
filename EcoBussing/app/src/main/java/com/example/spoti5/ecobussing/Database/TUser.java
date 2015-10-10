package com.example.spoti5.ecobussing.Database;

import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;

/**
 * Created by matildahorppu on 02/10/15.
 */
public class TUser extends User {
    private String email;
    private String name;
    private double currentDistance;        //Distance traveled by bus that has not yet been transferred to total, in KM.

    /**
     * Creates a user with username, email and password. This class does not check
     * username and password
     */

    public TUser(){

    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public double getCurrentDistance() {
        return currentDistance;
    }
}