package com.example.spoti5.ecobussing;





import com.example.spoti5.ecobussing.BusData.Bus;
import com.example.spoti5.ecobussing.BusData.Busses;
import com.example.spoti5.ecobussing.JsonClasses.EA.EARespond;
import com.example.spoti5.ecobussing.JsonClasses.VA.VANearbyStops;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by emilaxelsson on 24/09/15.
 */
public class BusConnection {

    public static void main(String[] args) {

        BusConnection demo = new BusConnection();
        try {
            demo.beginJourey(Busses.simulated);
            //demo.vasttrafikApi();

            //demo.getRespond(Sensor.NEXTSTOP);
            //demo.doGet();
            //String url = demo.getElecticityRequsetUrl(true,"Ericsson$GPS2", "001", System.currentTimeMillis()-(30*1000) ,System.currentTimeMillis());
        } catch (IOException e) {
        }

    }

    private Bus theBus;

    // Authorization-keys for the API's
    private final String EAkey = "Z3JwMjY6REhrdUFQRlZuIw==";
    private final String VAkey = "97ba5902-0424-4e46-9dad-4a5dadd218da";

    // Requestnames for the E-Api
    private final String GPS2 = "Ericsson$GPS2";


    /**
     * Called when the device is connected to a bus wifi.
     *
     * @param bus, the bus the device is connected to
     * @throws IOException
     */
    private void beginJourey(Bus bus) throws IOException {
        String vinNr = bus.getVIN();

        List<EARespond> gpsInfo = getGPSInfo(vinNr);

        for(EARespond rsp: gpsInfo){
            System.out.println(rsp);
        }

        double lon = getEAValue("Longitude2_Value", gpsInfo);
        double lat = getEAValue("Latitude2_Value", gpsInfo);

        VANearbyStops nbs = getNearbyStops(lon, lat, 3);

        System.out.println(nbs.getLocationList().getStopLocation().get(0).getName());


    }

    public double getEAValue(String theValue, List<EARespond> list){
        double value = 0;

        for(EARespond ear: list){

            if(ear.getResourceSpec().equals(theValue)){
                value = Double.parseDouble(ear.getValue());
            }
        }

        return value;
    }

    /**
     * Get the GPS information from a specific bus. This
     * method uses the sensor Ericsson$GPS2 from the Electricity API
     *
     * @param vinNr, the vin-number of the bus
     *
     * @return a list of five instances that contains
     *          different information:
     *          Latitude, Longitude, Speed, Course, Altitude.
     *
     */
    public List<EARespond> getGPSInfo(String vinNr) throws IOException {
        long t2 = System.currentTimeMillis();
        long t1 = t2 - (30 * 1000);

        String url = getElecticityRequsetUrl(true, GPS2, vinNr, t1, t2);

        String response = getEAResponse(url);

        Type listOfTestObject = new TypeToken<List<EARespond>>(){}.getType();

        List<EARespond> list = Collections.synchronizedList(
                (List<EARespond>) new Gson().fromJson(response, listOfTestObject));


        list = sortObjectList(list);

        return list;
    }

    public List<EARespond> sortObjectList(List<EARespond> list){
        List<EARespond> newList = new ArrayList<EARespond>();

        for(int i = list.size()-1; i>=0; i--){
            if(!newList.contains(list.get(i))){
                newList.add(list.get(i));
            }
        }

        return newList;

    }

    /**
     * Used to get the right url, depending on what information you would like
     *
     * @param isSensor, whether it is a sensor or a resource
     * @param requestName, the name of the sensor/resource
     * @param vinNr, the vin-number of the bus
     * @param t1, timespan begin
     * @param t2, timespan end
     *
     * @return the request-url
     */
    public String getElecticityRequsetUrl(boolean isSensor, String requestName,
                                                    String vinNr, long t1, long t2){

        String url = "";
        if(isSensor){
            url = "https://ece01.ericsson.net:4443/ecity?dgw=Ericsson$Vin_Num_" + vinNr +
                    "&sensorSpec=" + requestName + "&t1=" +  t1 + "&t2=" + t2;
        }else{
            url = "https://ece01.ericsson.net:4443/ecity?dgw=Ericsson$Vin_Num_" + vinNr +
                    "&resourceSpec=" + requestName + "&t1=" +  t1 + "&t2=" + t2;
        }

        return url;
    }

    /**
     * Gets the information that were requested from the Electricity API
     *
     * @param url, the request url
     * @return a string with the response
     * @throws IOException
     */
    public String getEAResponse(String url) throws IOException {

        String response = "";

        // Used to point out the "resource" on internet
        URL requestURL = new URL(url);

        // Connect to the resource
        HttpsURLConnection con = (HttpsURLConnection) requestURL
                .openConnection();

        // Get info from the resource (POST if you want to send anything)
        con.setRequestMethod("GET");

        // Get access
        con.setRequestProperty("Authorization", "Basic " + EAkey);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        // Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));


        // Put all the text into a String
        String inputLine;
        while ((inputLine = in.readLine()) != null) {

            response += inputLine;
        }
        in.close();

        System.out.println(response);

        return response;
    }


    /**
     * Used to find out where the user has began its trip
     *
     * @param lon, longitude value of the bus
     * @param lat, latitude value of the bus
     * @param maxNo, maximum number of stoplocations
     * @return an object with all the stoplocations
     * @throws IOException
     */
    public VANearbyStops getNearbyStops(double lon, double lat, int maxNo) throws IOException {

        String url = "http://api.vasttrafik.se/bin/rest.exe/v1/location.nearbystops?" +
                "authKey=" + VAkey + "&originCoordLong=" + lon +
                "&originCoordLat=" + lat + "&maxNo=" + maxNo +
                "&format=json&jsonpCallback=processJSON";

        String jsonObject = getVAResponse(url);

        VANearbyStops ns = new Gson().fromJson(jsonObject, VANearbyStops.class);

        return ns;


    }

    /**
     * Used to get answers from the V-Api
     * @param url, the request url
     * @return the response as a jsonobject-string
     * @throws IOException
     */
    public String getVAResponse(String url) throws IOException {
        String response = "";
        URL requestURL = new URL(url);

        HttpURLConnection con = (HttpURLConnection) requestURL.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

        String inputline = "";
        while((inputline = in.readLine()) != null){
            System.out.println(inputline);
            response +=inputline;
        }

        response = getFixedVAResponse(response);

        return response;


    }

    public String getFixedVAResponse(String response){
        int start = 0;
        for(int i = 0; i < response.length(); i++){
            if(response.charAt(i)== '{'){
                start = i;
                break;
            }
        }
        int end = 0;
        for(int i = response.length()-1; i >= 0 ; i--){
            if(response.charAt(i)== '}'){
                end = i;
                break;
            }
        }

        response = response.substring(start, end + 1);

        return response;
    }


}
