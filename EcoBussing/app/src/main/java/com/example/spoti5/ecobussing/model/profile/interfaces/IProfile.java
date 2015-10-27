package com.example.spoti5.ecobussing.model.profile.interfaces;

import java.io.Serializable;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IProfile extends Serializable {

    String getName();

    Double getDistanceTraveled();

    void incCO2Saved(double distance);

    Double getCO2Saved(boolean avoidDatabaseUpload);
    Double getCO2SavedYear(Integer year);
    Double getCO2SavedMonth(Integer year, Integer month);
    Double getCO2SavedDate(Integer year, Integer month, Integer day);
    Double getCO2SavedPast7Days(boolean avoidDatabaseUpload);

    void setName(String name);
}