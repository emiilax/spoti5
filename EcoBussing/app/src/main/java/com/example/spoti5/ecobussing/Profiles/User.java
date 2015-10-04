package com.example.spoti5.ecobussing.Profiles;

/**
 * Created by erikk on 2015-09-16.
 */
public class User implements IUser {

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

    public User(){

    }

    public User(String email){
        this.email = email;
        this.distance = 10;
        this.currentDistance = 2.4;
        this.carbondioxideSaved = 0;
        this.moneySaved = 0;
        this.carPetrolConsumption = 0.5;
        this.age = 0;
        this.position = 0;
    }

    public User(String email, String name){
        this(email);
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
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
    public double getCarPetrolConsumption() { return carPetrolConsumption; }

    @Override
    public void setCarPetrolConsumption(double carPetrolConsumption) { this.carPetrolConsumption = carPetrolConsumption; }

    public int getAge() {
        return age;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void setMoneySaved(double moneySaved) {
        this.moneySaved = moneySaved;
    }

    @Override
    public void setCurrentDistance(double currentDistance) {
        this.currentDistance = currentDistance;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public void setCO2Saved(double CO2Saved) {
        this.carbondioxideSaved = CO2Saved;
    }

    @Override
    public void decDistance(double reducedDistance) {
        distance = distance + reducedDistance;
    }

    @Override
    public void updateDistance() {
        distance = distance + currentDistance;
    }

    @Override
    public void resetCurrentDistance() {
        currentDistance = 0;
    }

    @Override
    public void incCurrentDistance(double addedDistance) {
        if (addedDistance > 0) {
            currentDistance = currentDistance + addedDistance;
        }
    }

    @Override
    public void incMoneySaved(double moneySaved) {
        if (moneySaved > 0) {
            this.moneySaved = this.moneySaved + moneySaved;
        }
    }

    @Override
    public void incCO2Saved(double carbonSaved) {
        if (carbonSaved > 0) {
            carbondioxideSaved = carbondioxideSaved + carbonSaved;
        }
    }
}
