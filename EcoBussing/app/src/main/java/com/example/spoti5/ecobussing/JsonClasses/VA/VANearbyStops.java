package com.example.spoti5.ecobussing.JsonClasses.VA;

/**
 * Created by Emil Axelsson on 01/10/15.
 *
 * Used when converting a Json-object from the Vasttrafik-api to a java object.
 */
public class VANearbyStops {

    /** List with all the locations from the response */
    private com.example.spoti5.ecobussing.JsonClasses.VA.LocationList LocationList;


    public LocationList getLocationList() {
        return LocationList;
    }

    public void setLocationList(LocationList locationlist) {
        LocationList = locationlist;
    }

    public StopLocation getStopLocation(int i){
        return LocationList.getStopLocation().get(i);
    }
}
