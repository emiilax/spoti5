package com.example.spoti5.ecobussing.Profiles;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IUser extends IProfile {

    public String getEmail();

    public void incCurrentDistance(double addedDistance);
    public void resetCurrentDistance();
    public double getCurrentDistance();

    public void incMoneySaved(double distance);

    public Double getMoneySaved();
    public Double getMoneySavedYear(Integer year);
    public Double getMoneySavedMonth(Integer year, Integer month);
    public Double getMoneySavedDate(Integer year, Integer month, Integer day);
    public Double getMoneySavedPast7Days();

    public String getCompany();

    public void setCompany(String name);
}
