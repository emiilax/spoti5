package com.example.spoti5.ecobussing;





import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.JsonReader;
import android.view.textservice.SentenceSuggestionsInfo;


import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;
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

            //demo.getRespond(Sensor.NEXTSTOP);
            //demo.doGet();
            //String url = demo.getElecticityRequsetUrl(true,"Ericsson$GPS2", "001", System.currentTimeMillis()-(30*1000) ,System.currentTimeMillis());
            //System.out.println(demo.getElectricityResponse(url));
        } catch (IOException e) {
        }

    }

    public enum Sensor {
        NEXTSTOP, GPS ;

    }
    private Bus theBus;

    private final String key = "Z3JwMjY6REhrdUFQRlZuIw==";

    private final String baseURL = "https://ece01.ericsson.net:4443/ecity?dgw=Ericsson$Vin_Num_";

    private final String sensorSpec = "sensorSpec=Ericsson$";

    // Request - names
    private final String GPS2 = "Ericsson$GPS2";


    private void beginJourey(Bus bus) throws IOException {
        String vinNr = bus.getVIN();

        List<RespondConverter> gpsInfo = getGPSInfo(vinNr);

        for(RespondConverter rsp: gpsInfo){
            System.out.println(rsp);
        }


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
    public List<RespondConverter> getGPSInfo(String vinNr) throws IOException {
        long t2 = System.currentTimeMillis();
        long t1 = t2 - (30 * 1000);

        String url = getElecticityRequsetUrl(true, GPS2, vinNr, t1, t2);

        String response = getElectricityResponse(url);

        List<String> responseValues = splitTheResponse(response);

        List<RespondConverter> respondObjects = createRespondObjects(responseValues);

        return respondObjects;
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
    public String getElectricityResponse(String url) throws IOException {

        String response = "";

        // Used to point out the "resource" on internet
        URL requestURL = new URL(url);

        // Connect to the resource
        HttpsURLConnection con = (HttpsURLConnection) requestURL
                .openConnection();

        // Get info from the resource (POST if you want to send anything)
        con.setRequestMethod("GET");

        // Get access
        con.setRequestProperty("Authorization", "Basic " + key);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        // Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));

        // put all the text into a String
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response += inputLine;
        }
        in.close();

        return response;
    }




    /**
     * Creates objects from the fixed response-json-objects
     * Uses Gson to convert from the json-string to an object
     *
     * Used when a sensor replys with more than one Json object
     *
     * @param theResponds, the json objects
     * @return a list of all the RespondConverts that creates
     */
    public List<RespondConverter> createRespondObjects(List<String> theResponds){
        List<RespondConverter> list = new ArrayList<RespondConverter>();

        for(int i = theResponds.size()-1; i>=0; i--){
            RespondConverter respConv = new Gson().fromJson(theResponds.get(i),
                    RespondConverter.class);

            if(!(list.contains(respConv))){
                list.add(respConv);
            }

        }
        return list;
    }

    /**
     * Split the string from the response and turn it into
     * json objects
     *
     * @param str, the response
     * @return a list of json objects
     */
    private List<String> splitTheResponse(String str){

        List<Integer> subStart = new ArrayList<Integer>();
        List<Integer> subEnd = new ArrayList<Integer>();
        int posS = 0;
        int posE = 0;
        List<String> arrays = new ArrayList<String>();
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == '{'){
                subStart.add(i);
                posS++;
            }else if(str.charAt(i) == '}'){
                subEnd.add(i + 1);
                posE++;
            }
        }
        //str = str.substring(0, str.length() + 1);
        str = str.replace('\"','\'');
        for(int i = 0; i < subStart.size(); i++){
            arrays.add(str.substring(subStart.get(i), subEnd.get(i)));
        }
        return arrays;
    }

    private void vasttrafikApi() throws IOException {

        StringBuffer response = new StringBuffer();
        String key = "97ba5902-0424-4e46-9dad-4a5dadd218da";

        String url =
                "http://api.vasttrafik.se/bin/rest.exe/v1/location.name?"+
                        "authKey=97ba5902-0424-4e46-9dad-4a5dadd218da" +
                        "&format=json&jsonpCallback=processJSON&input=kungsportsplatsen";


        URL requestURL = new URL(url);

        HttpURLConnection con = (HttpURLConnection) requestURL.openConnection();

        //con.setReadTimeout(10000);
        //con.setConnectTimeout(15000);
        con.setRequestMethod("GET");
        //con.setRequestProperty("Authorization", key);
        //con.setDoInput(true);
        //con.connect();

        System.out.println(con.getPermission()); // says "("java.net.SocketPermission" "api.vasttrafik.se:80" "connect,resolve")"



        System.out.println(con.getRequestMethod());

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        System.out.println(con.getResponseMessage());


        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

        String inputline = "";

        while((inputline = in.readLine()) != null){
            System.out.println(inputline);
            response.append(inputline);
        }
        in.close();

        System.out.println(response.toString());

    }


    private void doGet() throws IOException {
        long t2 = System.currentTimeMillis();
        long t1 = t2 - (60 * 1000);

        StringBuffer response = new StringBuffer();
        //TODO Enter your base64 encoded Username:Password
        String key = "Z3JwMjY6REhrdUFQRlZuIw==";
        String url = "https://ece01.ericsson.net:4443/ecity?dgw=Ericsson$Vin_Num_001&sensorSpec=Ericsson$Next_Stop&t1="
                + t1 + "&t2=" + t2;

        URL requestURL = new URL(url);



        HttpsURLConnection con = (HttpsURLConnection) requestURL
                .openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Basic " + key);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));

        String inputLine;

        //InputStream inp =


        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();



        String str = response.toString();
        //str = str.replace('\"','\'');
        //str = str.substring(1,118);
        System.out.println(str);
        //str = fixTheArray(str);


        System.out.println(str);
        //System.out.println(response.toString());
        RespondConverter respConv = new Gson().fromJson(str, RespondConverter.class);

        System.out.println(respConv);



    }



    public List<RespondConverter> getRespond(Sensor sensor) throws IOException {
        return getRespond("001", sensor);
    }

    public List<RespondConverter> getRespond(String vinNum, Sensor sensor) throws IOException {
        String url = "";
        String response = "";
        List<RespondConverter> list = new ArrayList<RespondConverter>();

        long t2 = System.currentTimeMillis();
        long t1 = t2 - (60 * 1000);

        switch(sensor){
            case NEXTSTOP:
                url = baseURL + vinNum + "&" + sensorSpec + "Next_Stop" + "&t1=" +  t1 + "&t2=" + t2;
            case GPS:
                url = baseURL + vinNum + "&" + sensorSpec + "GPS" + "&t1=" +  t1 + "&t2=" + t2;
        }

        URL requestURL = new URL(url);


        HttpsURLConnection con = (HttpsURLConnection) requestURL
                .openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Basic " + key);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));

        String inputLine;

        //InputStream inp =


        while ((inputLine = in.readLine()) != null) {
            response += inputLine;
        }
        in.close();
        System.out.println(response);
        for(String str: splitTheResponse(response)){
            System.out.println(str);
            RespondConverter respConv = new Gson().fromJson(str, RespondConverter.class);
            if(!(list.contains(respConv))){
                list.add(respConv);
            }
        }
        // response = fixTheArray(response);
        for(RespondConverter r: list){
            System.out.println(r);
        }
        //System.out.println(response);
        //RespondConverter respConv = new Gson().fromJson(response, RespondConverter.class);


        return list;
    }











}
