package com.example.spoti5.ecobussing.model.jsonclasses.googleapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hampus on 2015-10-02.
 */
public class EndLocation {

    @SerializedName("lat")
    @Expose
    public Double lat;
    @SerializedName("lng")
    @Expose
    public Double lng;

}