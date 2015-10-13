package com.example.spoti5.ecobussing.JsonClasses.VA;

/**
 * Created by emilaxelsson on 01/10/15.
 */
public class VANearbyStops {

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
