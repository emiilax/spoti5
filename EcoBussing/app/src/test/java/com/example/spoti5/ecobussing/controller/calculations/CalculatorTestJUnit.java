package com.example.spoti5.ecobussing.controller.calculations;

import com.example.spoti5.ecobussing.io.net.apirequest.Calculator;
import com.example.spoti5.ecobussing.model.jsonclasses.vastapi.StopLocation;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Hampus on 2015-10-11.
 */
public class CalculatorTestJUnit{

    Calculator testCalc = Calculator.getCalculator();
    int dist;
    double goodOriginLat = 57.7074868;
    double goodOriginLng = 11.939063;
    double goodDestinationLat = 57.6913241;
    double goodDestinationLng = 11.9669449;

    @Test
    public void calculatingDistanceBetweenTheSameLocationsShouldReturnZero() throws Exception {
        dist = 12;
        dist = testCalc.calculateDistance(goodOriginLat, goodOriginLng, goodOriginLat, goodOriginLng);
        assertTrue(dist == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculatingDistanceWithToBigLatValueShouldThrowException() throws IllegalArgumentException{
        dist = 12;
        dist = testCalc.calculateDistance(goodOriginLat, goodOriginLng, 92.132512, goodDestinationLng);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculatingDistanceWithToBigLngValueShouldThrowException() throws IllegalArgumentException{
        dist = 12;
        dist = testCalc.calculateDistance(goodOriginLat, 181.0, goodDestinationLat, goodDestinationLng);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculatingDistanceWithToSmallLatValueShouldThrowException() throws IllegalArgumentException{
        dist = 12;
        dist = testCalc.calculateDistance(-91.0, goodOriginLng, goodDestinationLat, goodDestinationLng);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculatingDistanceWithToSmallLngValueShouldThrowException() throws IllegalArgumentException{
        dist = 12;
        dist = testCalc.calculateDistance(goodOriginLat, goodOriginLng, goodDestinationLat, -181.0);
    }

    @Test
    public void calculatingDistanceBetweenTwoPointsThatHaveNoRouteShouldReturnANegativeValue(){
        dist = -12;
        // Values used here represent some place in Namibia, and somewhere in the water outside
        // Madagascar. And you can't drive a car between those two.
        dist = testCalc.calculateDistance(4.0, 12.9875, -13.0, 48.2342);
        assertTrue(dist == -1);
    }

    @Test
    public void calculationWithDifferentValuesShouldReturnGreaterThanZero(){
        dist = -12;
        dist = testCalc.calculateDistance(goodOriginLat, goodOriginLng, goodDestinationLat, goodDestinationLng);
        assertTrue(dist > 0);
    }

    @Test
    public void usingTwoStopLocationsToCalculateDistanceShouldBeTheSameAsUsingTheirCoordinates(){
        double coordDist = testCalc.calculateDistance(goodOriginLat, goodOriginLng, goodDestinationLat,
                goodDestinationLng);
        StopLocation origin = new StopLocation();
        StopLocation destination = new StopLocation();

        origin.setLat(Double.toString(goodOriginLat));
        origin.setLon(Double.toString(goodOriginLng));
        destination.setLat(Double.toString(goodDestinationLat));
        destination.setLon(Double.toString(goodDestinationLng));

        double stopLocationDist = testCalc.calculateDistance(origin, destination);

        assertTrue(coordDist == stopLocationDist);
    }







}