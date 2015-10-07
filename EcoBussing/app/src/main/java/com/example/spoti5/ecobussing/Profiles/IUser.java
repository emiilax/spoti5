package com.example.spoti5.ecobussing.Profiles;

import java.io.Serializable;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IUser extends IProfile {

    public double getCarPetrolConsumption();


    public String getEmail();

    public void incCurrentDistance(double addedDistance);
    public void resetCurrentDistance();
    public double getCurrentDistance();

    public void incCO2Saved(double carbonSaved);

    public int getAge();
    public int getPosition();
    public String getCompany();
    public String getCompanyKey();

    public void setCarPetrolConsumption(double carPetrolConsumption);
    public void setAge(int age);
    public void setPosition(int position);
    public void setMoneySaved(double moneySaved);
    public void setCurrentDistance(double currentDistance);
    public void setCompany(String name);
    public void setCompanyKey(String key);

    public void incMoneySaved(double moneySaved);
    public double getMoneySaved();
}
