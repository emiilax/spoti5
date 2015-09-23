package com.example.spoti5.ecobussing.Calculations;

/**
 * Created by hilden on 2015-09-16.
 */
public class Calculator {

    private static Calculator calculator;

    private double petrolPrice = 14.32;

    public static Calculator getCalculator() {
        if (calculator == null) {
            calculator = new Calculator();
        }
        return calculator;
    }

    public void calculateDistance() {}

    public double moneySaved(double distanceKM, double petrolConsumption) {
        return distanceKM*petrolPrice*petrolConsumption/10;
    }

    public double carbonSaved(double distanceKM, double carbondioxideWaste) {
        return distanceKM*carbondioxideWaste/10;
    }
}
