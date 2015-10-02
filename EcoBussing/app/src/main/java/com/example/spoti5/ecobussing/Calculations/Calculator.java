package com.example.spoti5.ecobussing.Calculations;

import com.example.spoti5.ecobussing.JsonClasses.Directions.Directions;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * Created by hilden on 2015-09-16.
 * Move into user??
 */
public class Calculator {

    private static Calculator calculator;

    private double petrolPrice = 14.32;
    private double carbondioxideEmittedPerLiter = 2.348; //Currently measured in grams.

    private String baseURL = "https://maps.googleapis.com/maps/api/directions/json?";

    public static Calculator getCalculator() {
        if (calculator == null) {
            calculator = new Calculator();
        }
        return calculator;
    }

    public int calculateDistance(double startLat, double startLng, double endLat, double endLng,
                                    String key) {
        int dist = 0;

        String completeURL = baseURL + "origin=" + startLat + "," + startLng + "&destination=" +
                endLat + "," + endLng +"&key=" + key;

        try{
            String jsonString = readUrl(completeURL);
            Gson gson = new Gson();
            Directions directions = gson.fromJson(jsonString, Directions.class);
            dist = directions.routes.get(0).legs.get(0).distance.value;
        }catch(IOException e){
            System.out.println(e);
        }catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return dist;
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    /**
     * @returns the amount of money saved since last startup in KR.
     * Pulls information from the current users "currentdistance" variable.
     */
    public double getCurrentMoneySaved() {

        return (SaveHandler.getCurrentUser().getCurrentDistance()/10)
                *petrolPrice*SaveHandler.getCurrentUser().getCarPetrolConsumption();
    }

    /**
     * @returns the amount of carbondioxide saved since last startup in milligrams.
     * Pulls information from the current users "currentdistance" variable.
     */
    public double getCurrentCarbonSaved() {
        return (SaveHandler.getCurrentUser().getCurrentDistance()/10)*SaveHandler.getCurrentUser().getCarPetrolConsumption()
                *carbondioxideEmittedPerLiter*1000;
    }
}
