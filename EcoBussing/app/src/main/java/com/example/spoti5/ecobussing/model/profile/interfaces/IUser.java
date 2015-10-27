package com.example.spoti5.ecobussing.model.profile.interfaces;

import com.example.spoti5.ecobussing.model.profile.DatabaseUser;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IUser extends IProfile {

    String getEmail();

    void incCurrentDistance(double addedDistance);
    void resetCurrentDistance();
    double getCurrentDistance();

    void incMoneySaved(double distance);
    void newJourney(double distance);

    double getCo2CurrentYear();
    double getCo2CurrentMonth();
    double getCo2Tot();

    Double getMoneySaved();
    Double getMoneySavedYear(Integer year);
    Double getMoneySavedMonth(Integer year, Integer month);
    Double getMoneySavedDate(Integer year, Integer month, Integer day);


    Double getMoneySavedPast7Days();
    int getTotaltTimesTraveled();
    DatabaseUser getDatabaseUser();


    String getCompany();
    void setCompany(String name);
}
