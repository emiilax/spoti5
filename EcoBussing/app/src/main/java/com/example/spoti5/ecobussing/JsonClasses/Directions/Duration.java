package com.example.spoti5.ecobussing.JsonClasses.Directions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hampus on 2015-10-02.
 */
public class Duration {

    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("value")
    @Expose
    public Integer value;

}
