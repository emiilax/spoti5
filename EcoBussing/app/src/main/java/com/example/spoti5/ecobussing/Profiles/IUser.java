package com.example.spoti5.ecobussing.Profiles;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IUser extends IProfile {

    public double getCarPetrolConsumption();
    public void setCarPetrolConsumption(double carPetrolConsumption);

    public String getEmail();

    public void incCurrentDistance(double addedDistance);
    public void resetCurrentDistance();
    public double getCurrentDistance();

    public void incCO2Saved(double carbonSaved);

    public int getAge();
    public int getPosition();

    public void setAge(int age);
    public void setPosition(int position);
    public void incMoneySaved(double moneySaved);
    public double getMoneySaved();
}
