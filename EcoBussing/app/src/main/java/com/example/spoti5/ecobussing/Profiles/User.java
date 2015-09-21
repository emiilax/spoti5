package com.example.spoti5.ecobussing.Profiles;

/**
 * Created by erikk on 2015-09-16.
 */
public class User implements IUser {

    private String email;
    private String username;
    private String password;
    private String name;
    private double distance;               //Total distance traveled by bus.
    private double currentDistance;        //Distance traveled by bus that has not yet been converted into co2 and money.
    private double carbondioxideSaved;     //Amount of carbondioxide saved.
    private double moneySaved;             //Amount of money saved.

    /**
     * Creates a user with username, email and password. This class does not check
     * username and password
     * @param username Has to be checked before entered here
     * @param email Has to cbe checked before entered here
     * @param password Has to be checked before entered here
     */
    public User(String username, String email, String password){
        this.username = username;
        this.password = password;
        this.email = email;
        this.distance = 0;
        this.currentDistance = 0;
        this.carbondioxideSaved = 0;
        this.moneySaved = 0;
    }

    public User(String username, String email, String password, String name){
        this(username, email, password);
        this.name = name;
        this.distance = 0;
        this.currentDistance = 0;
        this.carbondioxideSaved = 0;
        this.moneySaved = 0;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getDistance() {
        return distance;
    }

    @Override
    public double getCurrentDistance() {
        return currentDistance;
    }

    @Override
    public double getCO2Saved() {
        return carbondioxideSaved;
    }

    @Override
    public double getMoneySaved() {
        return moneySaved;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean checkPassword(String password) {
        return this.password.equals((String)password);
    }

    @Override
    public void incMoneySaved(double moneySaved) {
        this.moneySaved = this.moneySaved + moneySaved;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void incDistance(double addedDistance) {
        distance = distance + addedDistance;
    }

    @Override
    public void decDistance(double reducedDistance) {
        distance = distance + reducedDistance;
    }

    @Override
    public void incCurrentDistance(double addedDistance) {
        currentDistance = currentDistance + addedDistance;
    }

    @Override
    public void resetCurrentDistance() {
        currentDistance = 0;
    }

    @Override
    public void incCO2Saved(double carbonSaved) {
        carbondioxideSaved = carbondioxideSaved + carbonSaved;
    }

}
