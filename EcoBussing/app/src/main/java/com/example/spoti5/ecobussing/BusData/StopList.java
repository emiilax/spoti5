package com.example.spoti5.ecobussing.BusData;

import com.example.spoti5.ecobussing.JsonClasses.VA.StopLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilaxelsson on 07/10/15.
 *
 * Class that contains a Stoplocation list. Used i Busses55Stops.
 */
public class StopList {

    List<StopLocation> StopLocation = new ArrayList<StopLocation>();

    public void setStopLocation(List<StopLocation> stopLocation) {
        StopLocation = stopLocation;
    }

    public List<StopLocation> getStopLocation() { return StopLocation; }
}
