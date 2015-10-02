package com.example.spoti5.ecobussing.Tests;

import com.example.spoti5.ecobussing.Calculations.Calculator;
import com.example.spoti5.ecobussing.Profiles.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hilden on 2015-09-29.
 */
public class CalculatorTest {

    Calculator calculator = Calculator.getCalculator();
    List<Double> distances = new ArrayList<Double>();
    User testUser = new User("mail@mail.com");

    public CalculatorTest() {
        addDistances();
        applyDistances();
    }


    private void addDistances() {
        distances.add(0.31);
        distances.add(0.001);
        distances.add(1003.2);
        distances.add(134.0);
        distances.add(15.4);
        distances.add(-11.3);
        distances.add(0.0);
        distances.add(-12345.313);
    }

    private void applyDistances() {
        for (int i = 0; i < distances.size(); i++) {
            testUser.incCurrentDistance(distances.get(i));
            System.out.println("-------------------------------------------");
            System.out.println("Testcase: " + Integer.toString(i+1) + ", " + Double.toString(distances.get(i)));
            System.out.println("Currently saved CO2: " + Double.toString(calculator.getCurrentCarbonSaved()));
            System.out.println("Currently saved $$$: " + Double.toString(calculator.getCurrentMoneySaved()));
            System.out.println("-------------------------------------------");
        }

        System.out.println("ANSWERS:");
    }

    public static void main(String Args[]) {
        new CalculatorTest();
    }
 }
