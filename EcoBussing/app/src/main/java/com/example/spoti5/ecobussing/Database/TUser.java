package com.example.spoti5.ecobussing.Database;

import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;

/**
 * Created by matildahorppu on 02/10/15.
 */
public class TUser extends User {
    private String email;
    private String name;
    private int age;
    private int position; //position in toplist
    private double distance;               //Total distance traveled by bus, in KM.
    private double currentDistance;        //Distance traveled by bus that has not yet been transferred to total, in KM.
    private double carbondioxideSaved;     //Amount of carbondioxide saved.
    private double moneySaved;             //Amount of money saved in KR.
    private double carPetrolConsumption;   //Liters of gas required to drive one european mile.

    /**
     * Creates a user with username, email and password. This class does not check
     * username and password
     * @param email Has to cbe checked before entered here
     */

    public TUser(){

    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    public double getCurrentDistance() {
        return currentDistance;
    }

    public double getCO2Saved() {
        return carbondioxideSaved;
    }

    public double getMoneySaved() {
        return moneySaved;
    }

    public double getCarPetrolConsumption() { return carPetrolConsumption; }

    public int getPosition() {
        return position;
    }

}
