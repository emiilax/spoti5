package com.example.spoti5.ecobussing.Calculations;

import com.example.spoti5.ecobussing.SavedData.SaveHandler;

/**
 * Created by hilden on 2015-09-16.
 * Move into user??
 */
public class Calculator {

    private static Calculator calculator;

    private double petrolPrice = 14.32;
    private double carbondioxideEmittedPerLiter = 2.348; //Currently measured in grams.

    public static Calculator getCalculator() {
        if (calculator == null) {
            calculator = new Calculator();
        }
        return calculator;
    }

    public void calculateDistance() {}

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
