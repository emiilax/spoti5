package com.example.spoti5.ecobussing.model.profile.interfaces;

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


    /**
     *
     * @param avoidDatabaseUpload doesn't matter if true or false, this avoids MoneySaved to be stored in the database.
     * @return total money saved
     */
    Double getMoneySaved(boolean avoidDatabaseUpload);
    Double getMoneySavedYear(Integer year);
    Double getMoneySavedMonth(Integer year, Integer month);
    Double getMoneySavedDate(Integer year, Integer month, Integer day);

    /**
     *
     * @param avoidDatabaseUpload doesn't matter if true of false, this avoids MoneySavedPast7Days to be stored in the database.
     * @return total money saved
     */
    Double getMoneySavedPast7Days(boolean avoidDatabaseUpload);
    int getTotaltTimesTraveled(boolean avoidDatabaseUpload);

    String getCompany();

    void setCompany(String name);
}
