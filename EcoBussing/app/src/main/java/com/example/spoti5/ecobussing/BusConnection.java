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
            demo.getRespond(Sensor.NEXTSTOP);
            //demo.doGet();
        } catch (IOException e) {
        }

    }

    public enum Sensor {
        NEXTSTOP, GPS ;

    }
    private final String key = "Z3JwMjY6REhrdUFQRlZuIw==";

    private final String baseURL = "https://ece01.ericsson.net:4443/ecity?dgw=Ericsson$Vin_Num_";

    private final String sensorSpec = "sensorSpec=Ericsson$";

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
        for(String str: getTheJsonStrings(response)){
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
    private List<String> getTheJsonStrings(String str){

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





    private void vasttrafikApi() throws IOException {

        StringBuffer response = new StringBuffer();
        String key = "97ba5902-0424-4e46-9dad-4a5dadd218d";

        String url = "http://api.vasttrafik.se/bin/rest.exe/v1/location.name?authKey=97ba5902-0424-4e46-9dad-4a5dadd218d&format=json&jsonpCallback=processJSON&input=kungsportsplatsen";

        URL requestURL = new URL(url);


        HttpURLConnection con = (HttpURLConnection) requestURL.openConnection();
        System.out.println(con.getPermission());
        System.out.println(con.getRequestMethod());
        con.setRequestMethod("GET");
        //con.setRequestProperty("Accept-Charset", "UTF-8");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        System.out.println(con.getResponseMessage());


        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String respond = "";
        String respond2 = "";

        String inputline = "";
        String inputline2 = "";

        BufferedReader in2 = new BufferedReader(new InputStreamReader(requestURL.openStream()));
        while((inputline2 = in2.readLine()) != null){
            System.out.println(inputline2);
            respond2 += inputline;
        }


        while((inputline = in.readLine()) != null){
            System.out.println(inputline);
            respond += inputline;
        }
        in.close();

        System.out.println(respond.toString());
        //int responseCode = con.getResponseCode();



    }





}
