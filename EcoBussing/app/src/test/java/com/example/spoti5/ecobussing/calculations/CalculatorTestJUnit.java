package com.example.spoti5.ecobussing.calculations;

import com.example.spoti5.ecobussing.controller.calculations.Calculator;
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

    @Test
    public void calculatingDistanceBetweenTheSameLocationsShouldReturnZero() throws Exception {
        dist = 12;
        dist = testCalc.calculateDistance(57.7074868, 11.939063, 57.7074868, 11.939063);
        assertTrue(dist == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculatingDistanceWithToBigLatValueShouldThrowException() throws IllegalArgumentException{
        dist = 12;
        dist = testCalc.calculateDistance(57.7074868, 11.939063, 92.132512, 11.939063);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculatingDistanceWithToBigLngValueShouldThrowException() throws IllegalArgumentException{
        dist = 12;
        dist = testCalc.calculateDistance(4.0, 181.0, -13.0, 12.9875);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculatingDistanceWithToSmallLatValueShouldThrowException() throws IllegalArgumentException{
        dist = 12;
        dist = testCalc.calculateDistance(-91.0, 12.9875, -13.0, 12.9875);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculatingDistanceWithToSmallLngValueShouldThrowException() throws IllegalArgumentException{
        dist = 12;
        dist = testCalc.calculateDistance(4.0, 12.9875, -13.0, -181.0);
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
        dist = testCalc.calculateDistance(57.7074868, 11.939063,57.6913241,11.9669449);
        assertTrue(dist > 0);
    }







}