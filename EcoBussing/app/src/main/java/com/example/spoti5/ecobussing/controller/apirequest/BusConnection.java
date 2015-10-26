package com.example.spoti5.ecobussing.controller.apirequest;





import com.example.spoti5.ecobussing.model.bus.Bus;
import com.example.spoti5.ecobussing.model.bus.Bus55Stops;
import com.example.spoti5.ecobussing.controller.calculations.Calculator;
import com.example.spoti5.ecobussing.model.jsonclasses.eciapi.EARespond;
import com.example.spoti5.ecobussing.model.jsonclasses.vastapi.StopLocation;
import com.example.spoti5.ecobussing.model.jsonclasses.vastapi.VANearbyStops;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.controller.SaveHandler;
import com.example.spoti5.ecobussing.controller.listeners.NetworkStateChangeReciever;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by emilaxelsson on 24/09/15.
 */
public class BusConnection implements Runnable, PropertyChangeListener{


    public static void main(String[] args) throws IOException {

        Calendar cal = Calendar.getInstance();

        for(int i = 0; i < 24; i++){
            cal.add(Calendar.MONTH, -1);
            System.out.println(cal.get(Calendar.YEAR));
            System.out.println(cal.get(Calendar.MONTH));

        }

        /*
        List<String> busdwg = new ArrayList<>();

        for (Bus b : Busses.theBusses) {
            busdwg.add(b.getDwg());
        }

        for(String s: busdwg){
            System.out.println(s);

            System.out.println(s.equals(Busses.simulated.getDwg()));
        }

        */
        // System.out.println(busdwg.contains(Busses.simulated.getDwg()));

        //BusConnection demo = new BusConnection();

        //demo.beginJourey(Busses.eog604);


    }

    // Electricity-api connection
    private final ECIApi eciApi;

    // Västtrafik-api connection
    private final VATApi vatApi;

    private boolean stillConnected;

    // Holds the trips that not have been put into the database
    private final  List<List<StopLocation>> tempTripList;

    // Keeps the last location checked in run().
    private VANearbyStops lastLocations;

    private Bus currentBus;
    private boolean hasStarted = false;
    private boolean hasEnded = false;
    private StopLocation startLoc;
    private StopLocation endLoc;
    private double lastDistance = 0;

    public BusConnection(){
        eciApi = new ECIApi();
        vatApi = new VATApi();
        tempTripList = new ArrayList<>();
    }


    /**
     * Called when the device is connected to a bus wifi.
     *
     * @param bus, the bus the device is connected to
     * @throws IOException
     */
    public void beginJourey(Bus bus) throws IOException, NullPointerException {

        if(!hasStarted){
            currentBus = bus;
            String dwgNr = bus.getDwg();
            System.out.println(bus.getDwg());



            List<EARespond> gpsInfo = null;
            try {
                gpsInfo = eciApi.getGPSInfo(dwgNr);
            } catch(IllegalArgumentException e){
                return;
            }

            System.out.println("Journey begin");

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

            if(startLoc == null){
                throw new NullPointerException();
            }

            System.out.println("Startloc finished");

            new Thread(this).start();
            hasEnded = false;
            hasStarted = true;
        }




    }



    /**
     * Called when the journey ends. Defines endlocation and calculates the distance.
     *
     * @throws IOException
     */
    public void endJourney() throws IOException {

        if(!hasEnded && hasStarted){
            // Stops the thread
            stillConnected = false;

            hasEnded = true;

            // Finds the stoplocation closest to the buses location
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


            //endLoc = stopL;
            System.out.println(endLoc.getName());
            System.out.println("Endloc finished");

            // If there is network-connection, put it into the database.
            // Otherwise store it, and do it later.
            if(NetworkStateChangeReciever.getInstance().isNetwConnected()){
                double distance = Calculator.getCalculator().calculateDistance(startLoc, endLoc);
                System.out.println(distance);
                lastDistance = distance;
                // Update current user

                IUser usr = SaveHandler.getCurrentUser();
                System.out.println(usr.getCo2CurrentMonth());
                usr.newJourney(distance);

                SaveHandler.changeUser(usr);
                System.out.println(usr.getCo2CurrentMonth());
            }else{
                storeJourney(startLoc, endLoc);
            }
            hasStarted = false;

        }
        
    }

    /**
     * Called when there is network-connection available.
     * If there are any trips that are not in the database, put them there now
     */
    private void updateUser(){
        if(tempTripList.size()>0) {
            IUser usr = SaveHandler.getCurrentUser();
            for(int i = 0; i<tempTripList.size(); i++){
                StopLocation start = (tempTripList.get(i)).get(0);
                StopLocation stop = (tempTripList.get(i)).get(1);

                double distance = Calculator.getCalculator().calculateDistance(startLoc, endLoc);
                usr.newJourney(distance);
            }
            tempTripList.clear();
            SaveHandler.changeUser(usr);
        }
    }


    /**
     * Called when a trip is registred, but no network-connection
     * @param start, start location
     * @param stop, stoplocation
     */
    private void storeJourney(StopLocation start, StopLocation stop){

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
     * @return the requested value
     */
    private double getEAValue(String theValue, List<EARespond> list){
        double value = 0;

        for(EARespond ear: list){

            if(ear.getResourceSpec().equals(theValue)){
                value = Double.parseDouble(ear.getValue());
            }
        }

        return value;
    }




    /**
     * Checking the position every 5 second.
     */
    @Override
    public void run() {

        System.out.println("Run");

        while(NetworkStateChangeReciever.getInstance().isNetwConnected() && stillConnected){

            List<EARespond> gpsInfo = null;
            try {
                gpsInfo = eciApi.getGPSInfo(currentBus.getDwg());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException ex){
                ex.printStackTrace();
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
                } catch (Exception e) {
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

    // Getters
    public String getStartLoc(){

        return startLoc.getName();

    }

    public String getEndLoc(){
        return endLoc.getName();
    }

    public double getDistance(){
        return lastDistance;
    }


    // When the phone is connected to the network, this method is called.
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if(event.getPropertyName().equals("netwConnected")){
            updateUser();
        }
    }
}
