package com.example.spoti5.ecobussing.controller.apirequest;

import android.os.StrictMode;

import com.example.spoti5.ecobussing.Activites.ActivityController;
import com.example.spoti5.ecobussing.model.bus.Bus;
import com.example.spoti5.ecobussing.model.bus.Busses;
import com.example.spoti5.ecobussing.model.jsonclasses.eciapi.EARespond;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by emilaxelsson on 12/10/15.
 */
public class ECIApi {

    private final String EAkey = "Z3JwMjY6REhrdUFQRlZuIw==";

    // Requests
    private final String GPS2 = "Ericsson$GPS2";
    private List<String> requsts;


    private List<String> bussesDwg = new ArrayList<>();

    public ECIApi() {

        for (Bus b : Busses.theBusses) {
            bussesDwg.add(b.getDwg());
        }

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

    /**
     * Get the GPS information from a specific bus. This
     * method uses the sensor Ericsson$GPS2 from the Electricity API
     *
     * @param dwgNr, the dwg-number of the bus
     *
     * @return a list of five instances that contains
     *          different information:
     *          Latitude, Longitude, Speed, Course, Altitude.
     *
     */
    public List<EARespond> getGPSInfo(String dwgNr) throws IOException, IllegalArgumentException, NullPointerException {
        long t2 = System.currentTimeMillis();
        long t1 = t2 - (30 * 1000);

        String url = getRequestUrl(true, GPS2, dwgNr, t1, t2);

        System.out.println(url);

        String response = getEAResponse(url);
        System.out.println(response);

        Type listOfTestObject = new TypeToken<List<EARespond>>(){}.getType();

        List<EARespond> list = Collections.synchronizedList(
                    (List<EARespond>) new Gson().fromJson(response, listOfTestObject));




        list = sortObjectList(list);

        return list;
    }

    /**
     * Checks so there is only one object of ech value in the list.
     *
     * @param list, the list of objects
     * @return a list with one of each object
     */
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
     * @param dwgNr, the dwg-number of the bus
     * @param t1, timespan begin
     * @param t2, timespan end
     *
     * @return the request-url
     */
    public String getRequestUrl(boolean isSensor, String requestName,
                                String dwgNr, long t1, long t2) throws IllegalArgumentException{

        String url = "";



        if(!bussesDwg.contains(dwgNr)) throw new IllegalArgumentException();


        if(isSensor){
            url = "https://ece01.ericsson.net:4443/ecity?dgw=" + dwgNr +
                    "&sensorSpec=" + requestName + "&t1=" +  t1 + "&t2=" + t2;
        }else{
            url = "https://ece01.ericsson.net:4443/ecity?dgw=" + dwgNr +
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


        if(ActivityController.getContext() != null){

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);

        }


        URL requestURL = new URL(url);

        // Connect to the resource
        HttpsURLConnection con = (HttpsURLConnection) requestURL
                .openConnection();

        //con.setConnectTimeout(10000);
        //con.setReadTimeout(10000);
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



        // Used to point out the "resource" on internet
        return response;
    }
}
