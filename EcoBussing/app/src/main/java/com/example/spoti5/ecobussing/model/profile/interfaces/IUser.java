package com.example.spoti5.ecobussing.model.profile.interfaces;

import com.example.spoti5.ecobussing.Profiles.DatabaseUser;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IUser extends IProfile {

    public String getEmail();

    public void incCurrentDistance(double addedDistance);
    public void resetCurrentDistance();
    public double getCurrentDistance();

    public void incMoneySaved(double distance);
    public void newJourney(double distance);

    public double getCo2CurrentYear();
    public double getCo2CurrentMonth();
    public double getCo2Tot();



    public Double getMoneySaved();
    public Double getMoneySavedYear(Integer year);
    public Double getMoneySavedMonth(Integer year, Integer month);
    public Double getMoneySavedDate(Integer year, Integer month, Integer day);


    public Double getMoneySavedPast7Days();
    public int getTotaltTimesTraveled();
    public DatabaseUser getDatabaseUser();

    public String getCompany();

    public void setCompany(String name);
}
