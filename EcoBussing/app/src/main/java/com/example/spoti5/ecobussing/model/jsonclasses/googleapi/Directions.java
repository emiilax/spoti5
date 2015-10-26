package com.example.spoti5.ecobussing.model.jsonclasses.googleapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hampus on 2015-10-02.
 */
public class Directions {

    @SerializedName("geocoded_waypoints")
    @Expose
    public List<GeocodedWaypoint> geocodedWaypoints = new ArrayList<>();
    @SerializedName("routes")
    @Expose
    public List<Route> routes = new ArrayList<>();

}
