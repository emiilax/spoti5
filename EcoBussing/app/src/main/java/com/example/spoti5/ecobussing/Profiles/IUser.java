package com.example.spoti5.ecobussing.Profiles;

import java.io.Serializable;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IUser extends IProfile {

    public String getEmail();

    public void incCurrentDistance(double addedDistance);
    public void resetCurrentDistance();
    public double getCurrentDistance();

    public void incMoneySaved(double distance);

    /**
     *
     * @param avoidDatabaseUpload doesn't matter if true or false, this avoids MoneySaved to be stored in the database.
     * @return total money saved
     */
    public Double getMoneySaved(boolean avoidDatabaseUpload);
    public Double getMoneySavedYear(Integer year);
    public Double getMoneySavedMonth(Integer year, Integer month);
    public Double getMoneySavedDate(Integer year, Integer month, Integer day);

    /**
     *
     * @param avoidDatabaseUpload doesn't matter if true of false, this avoids MoneySavedPast7Days to be stored in the database.
     * @return total money saved
     */
    public Double getMoneySavedPast7Days(boolean avoidDatabaseUpload);

    public String getCompany();

    public void setCompany(String name);
}
