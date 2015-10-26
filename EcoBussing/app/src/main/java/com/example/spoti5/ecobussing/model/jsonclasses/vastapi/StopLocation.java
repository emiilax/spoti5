package com.example.spoti5.ecobussing.model.jsonclasses.vastapi;

/**
 * Created by Emil Axelsson on 01/10/15.
 *
 * Used when converting a Json-object from the Vasttrafik-api to a java object.
 */
public class StopLocation {


    private String name;

    private String lon;

    private String lat;

    private String id;

    private String idx;


    //Setters
    public void setName(String name) { this.name = name; }

    public void setLon(String lon) { this.lon = lon; }

    public void setLat(String lat) { this.lat = lat; }

    public void setId(String id) { this.id = id; }

    public void setIdx(String idx) { this.idx = idx;}

    //Getters
    public String getName() { return name; }

    public String getLon() { return lon; }

    public String getLat() { return lat; }

    public String getId() { return id; }

    public String getIdx() { return idx; }

    public boolean equals(Object o){

        return o instanceof StopLocation && this.getName().equals(((StopLocation)o).getName());

    }
}
