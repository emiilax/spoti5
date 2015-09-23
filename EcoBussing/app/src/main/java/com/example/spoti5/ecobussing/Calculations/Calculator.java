package com.example.spoti5.ecobussing.Calculations;

import com.example.spoti5.ecobussing.SavedData.SaveHandler;

/**
 * Created by hilden on 2015-09-16.
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

    public double getMoneySaved() {
        return (SaveHandler.getCurrentUser().getCurrentDistance()/10)
                *petrolPrice*SaveHandler.getCurrentUser().getCarPetrolConsumption();
    }

    public double getCarbonSaved() {
        return (SaveHandler.getCurrentUser().getCurrentDistance()/10)*SaveHandler.getCurrentUser().getCarPetrolConsumption()
                *carbondioxideEmittedPerLiter;
    }
}
