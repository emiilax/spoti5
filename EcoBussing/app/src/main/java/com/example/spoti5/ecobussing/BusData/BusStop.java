package com.example.spoti5.ecobussing.BusData;

/**
 * Created by Hampus on 2015-10-02.
 */
public class BusStop {
    private String name;
    private double lat;
    private double lng;

    public BusStop(String name, double lat, double lng){
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName(){return name;}
    public double getLat(){return lat;}
    public double getLng(){return lng;}
}
