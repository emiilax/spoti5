package com.example.spoti5.ecobussing.model.jsonclasses.vastapi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil Axelsson on 01/10/15.
 *
 * Used when converting a Json-object from the Vasttrafik-api to a java object.
 */
public class LocationList {

    private String noNamespaceSchemaLocation;

    private String servertime;

    private String serverdate;

    private List<com.example.spoti5.ecobussing.model.jsonclasses.vastapi.StopLocation> StopLocation = new ArrayList<com.example.spoti5.ecobussing.model.jsonclasses.vastapi.StopLocation>();

    // Setters
    public void setNoNamespaceSchemaLocation(String noNamespaceSchemaLocation) {
        this.noNamespaceSchemaLocation = noNamespaceSchemaLocation;
    }

    public void setServerdate(String serverdate) {
        this.serverdate = serverdate;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public void setStopLocation(List<com.example.spoti5.ecobussing.model.jsonclasses.vastapi.StopLocation> stopLocation) {
        StopLocation = stopLocation;
    }

    // Getters
    public String getNoNamespaceSchemaLocation() {
        return noNamespaceSchemaLocation;
    }

    public String getServerdate() {
        return serverdate;
    }

    public String getServertime() {
        return servertime;
    }

    public List<com.example.spoti5.ecobussing.model.jsonclasses.vastapi.StopLocation> getStopLocation() {
        return StopLocation;
    }
}
