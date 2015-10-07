package com.example.spoti5.ecobussing.Profiles;

import java.io.Serializable;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IProfile extends Serializable {

    public String getName();
    public double getDistance();
    public double getCO2Saved();

    public void setName(String name);
    public void setDistance(double distance);
    public void setCO2Saved(double CO2Saved);
    public void updateDistance();
    public void decDistance(double reducedDistance);
}
