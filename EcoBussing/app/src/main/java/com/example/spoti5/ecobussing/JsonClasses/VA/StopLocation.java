package com.example.spoti5.ecobussing.JsonClasses.VA;

/**
 * Created by emilaxelsson on 01/10/15.
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
        if(o instanceof StopLocation){
            return this.getName().equals(((StopLocation)o).getName());
        }
        return false;
    }
}
