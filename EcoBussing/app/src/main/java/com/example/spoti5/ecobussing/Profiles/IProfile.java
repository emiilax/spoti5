package com.example.spoti5.ecobussing.Profiles;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IProfile {

    public String getName();
    public double getDistance();
    public double getCO2Saved();

    public void setName(String name);
    public void updateDistance();
    public void decDistance(double reducedDistance);
}
