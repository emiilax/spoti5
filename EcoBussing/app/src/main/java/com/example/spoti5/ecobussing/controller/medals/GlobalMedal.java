package com.example.spoti5.ecobussing.controller.medals;

import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IUser;

import java.util.List;

/**
 * Created by Erik on 2015-10-17.
 * Contains all variables and data for global medal
 */
public class GlobalMedal {

    private static GlobalMedal instance;
    private IDatabase database;
    private double fullCO2Value;
    private double currentCO2Value;

    private int maxPeople;
    private int currentPeople;

    /**
     * Singelton constructor because all users have the same global medal
     * @return global medal instance
     */
    public static GlobalMedal getInstance(){
        if(instance == null){
            instance = new GlobalMedal();
        }
        return instance;
    }

    private GlobalMedal(){
        database = DatabaseHolder.getDatabase();
        co2TotMedal();
        peopleMedal();
    }

    //set value for co2 medal
    private void co2TotMedal(){
        fullCO2Value = 10000;
        currentCO2Value = 0;
        calculateCurrentCO2();
    }

    private void calculateCurrentCO2() {
        List<IUser> allUsers = database.getUsers();
        for(IUser u: allUsers){
            currentCO2Value = currentCO2Value + u.getCo2Tot();
        }
    }

    /**
     *
     * @return percentage done in decimals
     */
    public int getCO2TotPercentage(){
        return (int)((currentCO2Value/fullCO2Value)*100);
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

    //sets value for people medal
    private void peopleMedal() {
        maxPeople = 1000;
        currentPeople = 0;
        calculateCurrentPeople();
    }

    private void calculateCurrentPeople() {
        List<IUser> users = database.getUsers();
        currentPeople = users.size();
    }

    public int getPeoplePercantage(){
        return (currentPeople/maxPeople)*100;
    }

    public int getCurrentPeople() {
        return currentPeople;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }
}
