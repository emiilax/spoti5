package Calculations;

/**
 * Created by hilden on 2015-09-16.
 */
public class Calculator {

    private double petrolPrice = 14.32;

    public void calculateDistance() {}


    public double calculateMoney(double distanceKM, double petrolConsumption) {
        return distanceKM*petrolPrice*petrolConsumption/10;
    }

    public double calculateCarbon(double distanceKM, double carbondioxideWaste) {
        return distanceKM*carbondioxideWaste/10;
    }


}