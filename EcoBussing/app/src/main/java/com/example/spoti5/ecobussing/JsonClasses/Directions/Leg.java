package com.example.spoti5.ecobussing.JsonClasses.Directions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hampus on 2015-10-02.
 */
public class Leg {

    @SerializedName("distance")
    @Expose
    public Distance distance;
    @SerializedName("duration")
    @Expose
    public Duration duration;
    @SerializedName("end_address")
    @Expose
    public String endAddress;
    @SerializedName("end_location")
    @Expose
    public EndLocation endLocation;
    @SerializedName("start_address")
    @Expose
    public String startAddress;
    @SerializedName("start_location")
    @Expose
    public StartLocation startLocation;

}
