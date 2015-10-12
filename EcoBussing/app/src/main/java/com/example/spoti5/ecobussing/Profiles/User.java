package com.example.spoti5.ecobussing.Profiles;


import com.example.spoti5.ecobussing.Calculations.Calculator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;


/**
 * Created by erikk on 2015-09-16.
 */
public class User implements IUser{

    private String email;
    private String name;
    private String connectedCompany;       //The name of the company the user is connected to.
    private double currentDistance;        //Distance traveled by bus that has not yet been displayed to the user, in KM.

    private Calendar calendar = Calendar.getInstance();



    private long timeStampInMillis;
    private Integer stampedMonth;
    private Integer stampedYear;
    private Integer savedMonth;
    private Integer savedYear;
    private boolean firstUse;

    //Variables to store the total, this months and this years carbondioxide saved.
    //Needed to speed up the aquiring of this data to the toplists.
    private double co2CurrentMonth;
    private double co2CurrentYear;
    private double co2Tot;

    private String co2Json;

    private String moneyJson;

    private DeepMap<Integer, Integer, Integer, Double> co2SavedMap  = new DeepMap<>();
    private DeepMap<Integer, Integer, Integer, Double> moneySavedMap  = new DeepMap<>();

    public User(){}

    /**
     * Creates a user with username, email and password. This class does not check
     * username and password
     * @param email Has to cbe checked before entered here
     */
    public User(String email){
        this.email = email;
        this.currentDistance = 0;
        this.co2CurrentMonth = 0;
        this.co2CurrentYear = 0;
        this.co2Tot = 0;
        this.firstUse = true;

        this.connectedCompany = "";
        this.co2Json = getCo2Json();
    }

    public User(String email, String name){
        this(email);
        this.name = name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public double getCurrentDistance() {
        return currentDistance;
    }

    @Override
    public void resetCurrentDistance() {
        currentDistance = 0;
    }

    @Override
    public void incCurrentDistance(double addedDistance) {
        if (addedDistance > 0) {
            currentDistance = currentDistance + addedDistance;
        }
    }

    @Override
    public void setCompany(String name) {
        this.connectedCompany = name;
    }

    @Override
    public String getCompany() {
        return connectedCompany;
    }

    /**
     * Calculates the co2 saved from the distance and adds it into the current dates position in the DeepMap.
     * @param distance the distance to be calculated into CO2 saved and added.
     */
    @Override
    public void incCO2Saved(double distance) {
        double co2Saved = Calculator.getCalculator().calculateCarbonSaved(distance);
        co2SavedMap.addToCurrentDate(co2Saved);
        this.incCurrentDistance(distance);
        this.addToCurrentCO2Saved(co2Saved);
    }

    @Override
    public Double getCO2Saved(boolean avoidDatabaseUpload) {
        return co2SavedMap.getSumOfAllDates();
    }

    @Override
    public Double getCO2SavedYear(Integer year) {
        return co2SavedMap.getSumOfOneYear(year);
    }

    @Override
    public Double getCO2SavedMonth(Integer year, Integer month) {
        return co2SavedMap.getSumOfOneMonth(year, month);
    }

    @Override
    public Double getCO2SavedDate(Integer year, Integer month, Integer day) {
        return co2SavedMap.getSpecificDate(year, month, day);
    }

    @Override
    public Double getCO2SavedPast7Days(boolean avoidDatabaseUpload) {
        return co2SavedMap.getSumOfPastSevenDays();
    }

    /**
     * Helpmethod to the incCO2Saved method.
     * Adds the saved CO2 to the CO2CurrentMonth, CO2CurrentYear and CO2Tot variables.
     * @param co2Saved the CO2 to be added to the variables.
     */
    private void addToCurrentCO2Saved(double co2Saved) {
        timeStampInMillis = calendar.getTimeInMillis();
        calendar.setTimeInMillis(timeStampInMillis);
        stampedMonth = calendar.get(Calendar.MONTH) + 1;
        stampedYear = calendar.get(Calendar.YEAR);

        if (firstUse) {
            savedMonth = stampedMonth;
            savedYear = stampedYear;
            firstUse = false;
        }

        if (stampedYear != savedYear) {
            co2CurrentYear = 0;
            co2CurrentMonth = 0;
            savedYear = stampedYear;
            savedMonth = stampedMonth;
        } else {
            if (stampedMonth != savedMonth) {
                co2CurrentMonth = 0;
                savedMonth = stampedMonth;
            }
        }
        co2CurrentMonth = co2CurrentMonth + co2Saved;
        co2CurrentYear = co2CurrentYear + co2Saved;
        co2Tot = co2Tot + co2Saved;
    }

    public double getCo2CurrentYear() {
        return co2CurrentYear;
    }

    public double getCo2CurrentMonth() {
        return co2CurrentMonth;
    }

    public double getCo2Tot() {
        return co2Tot;
    }

    /**
     * Calculates the money saved from the distance and adds it into the current dates position in the DeepHashmap.
     * @param distance the distance to be calculated into money saved and added.
     */
    @Override
    public void incMoneySaved(double distance) {
        double moneySaved = Calculator.getCalculator().calculateMoneySaved(distance);
        moneySavedMap.addToCurrentDate(moneySaved);
    }

    @Override
    public Double getMoneySaved(boolean avoidDatabaseUpload) {
        return moneySavedMap.getSumOfAllDates();
    }

    @Override
    public Double getMoneySavedYear(Integer year) {
        return moneySavedMap.getSumOfOneYear(year);
    }

    @Override
    public Double getMoneySavedMonth(Integer year, Integer month) {
        return moneySavedMap.getSumOfOneMonth(year, month);
    }

    @Override
    public Double getMoneySavedDate(Integer year, Integer month, Integer day) {
        return moneySavedMap.getSpecificDate(year, month, day);
    }

    @Override
    public Double getMoneySavedPast7Days(boolean avoidDatabaseUpload) {
        return moneySavedMap.getSumOfPastSevenDays();
    }

    @Override
    public Double getDistanceTraveled() {
        return null;
    }

    @Override
    public String toString() {
        return "User{" +
                "moneyJson='" + moneyJson + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", connectedCompany='" + connectedCompany + '\'' +
                ", currentDistance=" + currentDistance +
                ", calendar=" + calendar +
                ", timeStampInMillis=" + timeStampInMillis +
                ", stampedMonth=" + stampedMonth +
                ", stampedYear=" + stampedYear +
                ", savedMonth=" + savedMonth +
                ", savedYear=" + savedYear +
                ", firstUse=" + firstUse +
                ", co2CurrentMonth=" + co2CurrentMonth +
                ", co2CurrentYear=" + co2CurrentYear +
                ", co2Tot=" + co2Tot +
                ", co2Json='" + co2Json + '\'' +
                '}';
    }

    public long getTimeStampInMillis() {
        return timeStampInMillis;
    }

    public Integer getStampedMonth() {
        return stampedMonth;
    }

    public Integer getStampedYear() {
        return stampedYear;
    }

    public Integer getSavedMonth() {
        return savedMonth;
    }

    public Integer getSavedYear() {
        return savedYear;
    }

    public boolean isFirstUse() {
        return firstUse;
    }

    public String getCo2Json() {
        Gson gson = new GsonBuilder().disableInnerClassSerialization().create();
        System.out.println(co2SavedMap.toString());
        String json =  gson.toJson(co2SavedMap);
        return json;
    }

    public String getMoneyJson() {
        Gson gson = new Gson();
        return gson.toJson(moneyJson);
    }
}
