package com.example.spoti5.ecobussing.controller.calculations;

import com.example.spoti5.ecobussing.model.jsonclasses.googleapi.Directions;
import com.example.spoti5.ecobussing.model.jsonclasses.vastapi.StopLocation;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by hilden on 2015-09-16.
 * Move into user??
 */
public class Calculator {

    private static Calculator calculator;

    private double petrolPrice = 14.32;
    private final double carbondioxideEmittedPerLiter = 2.348; //Currently measured in kg.
    private final double avgCarPetrolConsumption = 0.7;


    private final String baseURL = "https://maps.googleapis.com/maps/api/directions/json?";

    private final String key = "AIzaSyDFYgoDp2y2oL8JMyRyaVMRaQkBriCouNg";

    public static Calculator getCalculator() {
        if (calculator == null) {
            calculator = new Calculator();
        }
        return calculator;
    }

    public int calculateDistance(StopLocation origin, StopLocation destination){
        double originLat = Double.parseDouble(origin.getLat());
        double originLng = Double.parseDouble(origin.getLon());

        System.out.println("startLat: " + originLat + ", startLong: " + originLng);
        double destinationLat = Double.parseDouble(destination.getLat());
        double destinationLng = Double.parseDouble(destination.getLon());
        System.out.println("endLat: " + destinationLat + ", endLong: " + destinationLng);
        return calculateDistance(originLat, originLng, destinationLat, destinationLng);
    }

    /**
     * @param originLat must be >= -90.0 and <= 90.0
     * @param originLng must be >= -180.0 and <= 180.0
     * @param destinationLat must be >= -90.0 and <= 90.0
     * @param destinationLng must be >= -180.0 and <= 180.0
     * @return The distance for driving from the start position to the end position. Returns -1 if
     * any exception has been encountered.
     *
     * This method uses Google Maps Directions API to calculate the distance you whould have to drive
     * to get from one point to another.
     */
    public int calculateDistance(double originLat, double originLng, double destinationLat,
                                 double destinationLng) {
        int dist = 0;

        // Creates the complete URL used to get Json from Google Maps Directions API. Consists of the
        // baseURL, the doubles of origin and destination and the key.
        String completeURL = baseURL + "origin=" + originLat + "," + originLng + "&destination=" +
                destinationLat + "," + destinationLng +"&key=" + key;

        if (correctFormatLat(originLat) && correctFormatLng(originLng) && correctFormatLat(destinationLat) &&
                correctFormatLng(destinationLng)) {
            try {
                String jsonString = readUrl(completeURL);
                Gson gson = new Gson();
                Directions directions = gson.fromJson(jsonString, Directions.class);
                dist = directions.routes.get(0).legs.get(0).distance.value;

            } catch (IndexOutOfBoundsException e) {
                dist = -1;
                e.printStackTrace();
            } catch (IOException e) {
                dist = -1;
                e.printStackTrace();
            } catch (Exception e) {
                dist = -1;
                //System.out.println(e);
                e.printStackTrace();
            }
        }else{
                throw new IllegalArgumentException("Latitude or longitude values are too big or too small");
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
     * @return the amount of money in KR saved from the distance given.
     */
    public double calculateMoneySaved(Double distance) {
        return (distance/10000)*petrolPrice*avgCarPetrolConsumption;
    }

    /**
     * @return the amount of carbondioxide in milligrams saved from the distance given.
     */
    public double calculateCarbonSaved(Double distance) {
        return (distance/10000)*avgCarPetrolConsumption*carbondioxideEmittedPerLiter;
    }


    /**
     * Calculates the distance a user has traveled based on the stored co2 of the user.
     *
     * OBS! This method most likely needs an update when the rest of the Calculator methods get fixed
     * @param co2 how much c02
     * @return the distance, based on the parameter co2
     */
    public double calculateDistanceFromCO2(double co2) {
        return (co2*10000)/(avgCarPetrolConsumption*carbondioxideEmittedPerLiter);
    }

    /**
     *
     * @param lat is the double value that is checked.
     * @return True if the value is within the limits of a latitude. That is if it's greater than
     * -90 and smaller than 90.
     */
    private boolean correctFormatLat(double lat){
        return (lat >= -90 && lat <= 90);
    }

    /**
     *
     * @param lng is the double value that is checked.
     * @return True if the value is within the limits of a latitude. That is if it's greater than
     * -180 and smaller than 180.
     */
    private boolean correctFormatLng(double lng){
        return (lng >= -180 && lng <= 180);
    }

}
