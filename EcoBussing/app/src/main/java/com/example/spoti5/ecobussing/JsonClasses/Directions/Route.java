package com.example.spoti5.ecobussing.JsonClasses.Directions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hampus on 2015-10-02.
 */
public class Route {
    @SerializedName("legs")
    @Expose
    public List<Leg> legs = new ArrayList<Leg>();

}
