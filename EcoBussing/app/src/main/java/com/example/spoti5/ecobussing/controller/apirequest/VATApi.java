package com.example.spoti5.ecobussing.controller.apirequest;

import android.os.StrictMode;

import com.example.spoti5.ecobussing.Activites.ActivityController;
import com.example.spoti5.ecobussing.model.jsonclasses.vastapi.VANearbyStops;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by emilaxelsson on 12/10/15.
 */
public class VATApi {


    private final String VAkey = "97ba5902-0424-4e46-9dad-4a5dadd218da";


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


        if(ActivityController.getContext() != null){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }



        URL requestURL = new URL(url);

        HttpURLConnection con = (HttpURLConnection) requestURL.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

        String inputline = "";
        while((inputline = in.readLine()) != null){
            //System.out.println(inputline);
            response +=inputline;
        }

        response = getFixedVAResponse(response);

        return response;


    }

    /**
     * The response from the API do not work with the Gson-class method fromJson.
     * Therefore the response must be fixed.
     *
     * @param response, the response from the API
     * @return a cleaned up response
     */
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
