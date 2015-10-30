package com.example.spoti5.ecobussing.model.jsonclasses.googleapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Theses are classes that are used to store the data gotten from the Google Maps Directions API. The
 * classes were generated using http://www.jsonschema2pojo.org/ before they were moderated, or rather
 * stripped down, since there was no use for a large portion of the Google Maps data.
 * Created by Hampus on 2015-10-02.
 */
public class Distance {

    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("value")
    @Expose
    public Integer value;

}
