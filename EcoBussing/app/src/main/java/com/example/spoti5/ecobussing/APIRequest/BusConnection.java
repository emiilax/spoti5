package com.example.spoti5.ecobussing.APIRequest;





import com.example.spoti5.ecobussing.BusData.Bus;
import com.example.spoti5.ecobussing.BusData.Bus55Stops;
import com.example.spoti5.ecobussing.BusData.Busses;
import com.example.spoti5.ecobussing.Calculations.Calculator;
import com.example.spoti5.ecobussing.JsonClasses.EA.EARespond;
import com.example.spoti5.ecobussing.JsonClasses.VA.StopLocation;
import com.example.spoti5.ecobussing.JsonClasses.VA.VANearbyStops;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;
import com.example.spoti5.ecobussing.NetworkStateChangeReciever;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by emilaxelsson on 24/09/15.
 */
public class BusConnection implements Runnable, PropertyChangeListener{


    public static void main(String[] args) throws IOException {

        BusConnection demo = new BusConnection();
        //try {
        demo.beginJourey(Busses.simulated);
        //demo.vasttrafikApi();
        //demo.testVA();
        //new Thread(demo).start();
        //demo.getRespond(Sensor.NEXTSTOP);
        //demo.doGet();
        //String url = demo.getElecticityRequsetUrl(true,"Ericsson$GPS2", "001", System.currentTimeMillis()-(30*1000) ,System.currentTimeMillis());
        //} catch (IOException e) {
        //}

    }

    private NetworkStateChangeReciever wifiReciever;
    private Bus theBus;
    private ECIApi eciApi;
    private VATApi vatApi;

    private boolean stillConnected;
    // Authorization-keys for the API's
    private final String EAkey = "Z3JwMjY6REhrdUFQRlZuIw==";
    private final String VAkey = "97ba5902-0424-4e46-9dad-4a5dadd218da";

    // Requestnames for the E-Api
    private final String GPS2 = "Ericsson$GPS2";

    private Bus currentBus;
    boolean hasStarted = false;
    private StopLocation startLoc;
    private StopLocation endLoc;

    public BusConnection(){
        eciApi = new ECIApi();
        vatApi = new VATApi();
    }


    /**
     * Called when the device is connected to a bus wifi.
     *
     * @param bus, the bus the device is connected to
     * @throws IOException
     */
    public void beginJourey(Bus bus) throws IOException {

        if(!hasStarted){
            currentBus = bus;
            String dwgNr = bus.getDwg();

            System.out.println("Journey begin");

            List<EARespond> gpsInfo = eciApi.getGPSInfo(dwgNr);

            for(EARespond rsp: gpsInfo){
                System.out.println(rsp);
            }

            stillConnected = true;

            double lon = getEAValue("Longitude2_Value", gpsInfo);
            double lat = getEAValue("Latitude2_Value", gpsInfo);

            VANearbyStops locations = vatApi.getNearbyStops(lon, lat, 20);

            //startLoc = locations.getStopLocation(0);

            for(StopLocation nStop: locations.getLocationList().getStopLocation()){

                if(Bus55Stops.stops.contains(nStop)){
                    int i = Bus55Stops.stops.indexOf(nStop);
                    System.out.println(nStop.getName());
                    startLoc = Bus55Stops.stops.get(i);
                    System.out.println("found it!");
                    System.out.println(startLoc.getName());
                    break;
                }

            }

            System.out.println("Startloc finished");

            if(!hasStarted){
                new Thread(this).start();
            }
        }


        hasStarted = true;

    }

    boolean hasEnded = false;
    public void endJourney() throws IOException {

        if(!hasEnded){
            stillConnected = false;
            for(StopLocation nStop: lastLocations.getLocationList().getStopLocation()){

                if(Bus55Stops.stops.contains(nStop)){
                    int i = Bus55Stops.stops.indexOf(nStop);
                    System.out.println(nStop.getName());
                    endLoc = Bus55Stops.stops.get(i);
                    System.out.println("found it!");
                    System.out.println(startLoc.getName());
                    break;
                }
            }
            hasEnded = true;

            //endLoc = stopL;
            System.out.println(endLoc.getName());
            System.out.println("Endloc finished");

            if(NetworkStateChangeReciever.getInstance().isNetwConnected()){
                double distance = Calculator.getCalculator().calculateDistance(startLoc, endLoc);
                System.out.println(distance);

                // Update current user
                IUser usr = SaveHandler.getCurrentUser();
                usr.incCO2Saved(distance);

                SaveHandler.changeUser(usr);
            }else{
                storeJourney(startLoc, endLoc);
            }

        }
        
    }

    public void updateUser(){
        if(tempTripList.size()>0) {
            IUser usr = SaveHandler.getCurrentUser();
            for(int i = 0; i<tempTripList.size(); i++){
                StopLocation start = (tempTripList.get(i)).get(0);
                StopLocation stop = (tempTripList.get(i)).get(1);

                double distance = Calculator.getCalculator().calculateDistance(startLoc, endLoc);
                usr.incCO2Saved(distance);
            }
            tempTripList.clear();
            SaveHandler.changeUser(usr);
        }
    }

    List<List<StopLocation>> tempTripList = new ArrayList<>();
    public void storeJourney(StopLocation start, StopLocation stop){

        List<StopLocation> trip = new ArrayList<>();
        trip.add(start);
        trip.add(stop);

        tempTripList.add(trip);
    }




    /**
     * Used to get a specific value from the array of o
     * bjects that were created from the JSon-object
     *
     * @param theValue, the name of the value
     * @param list, the list of objects
     * @return
     */
    public double getEAValue(String theValue, List<EARespond> list){
        double value = 0;

        for(EARespond ear: list){

            if(ear.getResourceSpec().equals(theValue)){
                value = Double.parseDouble(ear.getValue());
            }
        }

        return value;
    }


    StopLocation stopL = null;
    VANearbyStops lastLocations = null;
    double currDist = 0;
    @Override
    public void run() {

        System.out.println("Run");

        while(NetworkStateChangeReciever.getInstance().isConnectedToWifi() && stillConnected){

            List<EARespond> gpsInfo = null;
            try {
                gpsInfo = eciApi.getGPSInfo(currentBus.getDwg());
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            for(EARespond rsp: gpsInfo){
                System.out.println(rsp);
            }
            */
            System.out.println("Check location");
            if(gpsInfo != null){
                double lon = getEAValue("Longitude2_Value", gpsInfo);
                double lat = getEAValue("Latitude2_Value", gpsInfo);

                try {
                    lastLocations = vatApi.getNearbyStops(lon, lat, 20);
                    //stopL = lastLocations.getStopLocation(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }




            try {
                synchronized (this) {
                    this.wait(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if(event.getPropertyName().equals("netwConnected")){
            updateUser();
        }
    }
}
