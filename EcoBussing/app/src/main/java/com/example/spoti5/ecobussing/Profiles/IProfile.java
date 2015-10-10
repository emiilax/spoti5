package com.example.spoti5.ecobussing.Profiles;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IProfile {

    public String getName();

    public Double getDistanceTraveled();

    public void incCO2Saved(double distance);

    public Double getCO2Saved();
    public Double getCO2SavedYear(Integer year);
    public Double getCO2SavedMonth(Integer year, Integer month);
    public Double getCO2SavedDate(Integer year, Integer month, Integer day);
    public Double getCO2SavedPast7Days();

    public void setName(String name);
}
