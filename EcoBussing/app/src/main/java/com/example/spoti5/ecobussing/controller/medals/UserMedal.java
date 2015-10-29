package com.example.spoti5.ecobussing.controller.medals;

import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IUser;

/**
 * Created by Erik on 2015-10-18.
 * Contains all variables and data for user medals
 */
public class UserMedal {

    private double fullCO2Value;
    private double currentCO2Value;

    private int maxTimesTravel;
    private int currentTimesTravel;

    private IUser user;

    public UserMedal(IUser user){
        this.user = user;

        co2TotMedal();
        travelMoreMedal();
    }

    //sets values for co2 total medal
    private void co2TotMedal() {
        fullCO2Value = 1000;
        currentCO2Value = user.getCo2Tot();
    }

    public int getCO2ToPercentage(){
        return (int)(currentCO2Value/fullCO2Value)*100;
    }

    public double getFullCO2Value() {
        return fullCO2Value;
    }

    public double getCurrentCO2Value() {
        return currentCO2Value;
    }

    public void setFullCO2Value(double fullCO2Value) {
        this.fullCO2Value = fullCO2Value;
    }


    //sets value for travel more medal
    private void travelMoreMedal() {
        maxTimesTravel = 100;
        currentTimesTravel = user.getTotaltTimesTraveled();
    }

    public int getTravelMorePercantage(){
        if(currentTimesTravel == 0){
            return 0;
        }
        return (currentTimesTravel/maxTimesTravel) * 100;
    }

    public int getMaxTimesTravel() {
        return maxTimesTravel;
    }

    public int getCurrentTimesTravel() {
        return currentTimesTravel;
    }

    public void setMaxTimesTravel(int maxTimesTravel) {
        this.maxTimesTravel = maxTimesTravel;
    }
}
