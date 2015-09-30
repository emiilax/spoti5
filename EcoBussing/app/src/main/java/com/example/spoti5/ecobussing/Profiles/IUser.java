package com.example.spoti5.ecobussing.Profiles;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IUser extends IProfile {

    public String getUsername();
    public void setUsername(String username);

    public double getCarPetrolConsumption();
    public void setCarPetrolConsumption(double carPetrolConsumption);

    public String getEmail();

    public void incCurrentDistance(double addedDistance);
    public void resetCurrentDistance();
    public double getCurrentDistance();

    public void incCO2Saved(double carbonSaved);

    public void setPassword(String password);
    public boolean checkPassword(String password);
    public boolean checkUsername(String username);
    public int getAge();
    public int getPosition();

    public void setAge(int age);
    public void setPosition(int position);
    public void incMoneySaved(double moneySaved);
    public double getMoneySaved();
}
