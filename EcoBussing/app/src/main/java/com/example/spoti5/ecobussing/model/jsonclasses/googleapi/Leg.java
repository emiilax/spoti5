package com.example.spoti5.ecobussing.model.jsonclasses.googleapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Theses are classes that are used to store the data gotten from the Google Maps Directions API. The
 * classes were generated using http://www.jsonschema2pojo.org/ before they were moderated, or rather
 * stripped down, since there was no use for a large portion of the Google Maps data.
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
