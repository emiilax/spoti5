package com.example.spoti5.ecobussing.model.profile;

import android.provider.ContactsContract;

import com.example.spoti5.ecobussing.model.profile.DeepMap;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Erik on 2015-10-20. If this class is edited it may cause the database to function
 * inproperly. It's a class storing data with getters and setters on the database
 */
public class DatabaseUser implements Serializable{

    private String email;
    private String name;
    private String company;       //The name of the company the user is connected to.
    private double currentDistance;

    private long timeStampInMillis;

    private boolean firstUse;

    private double co2CurrentMonth;
    private double co2CurrentYear;
    private double co2Tot;

    private String co2Json;
    private String moneyJson;

    private String oldCo2Json   = "";
    private String oldMoneyJson = "";

    private DeepMap<Integer, Integer, Integer, Double> co2SavedMap; //= new DeepMap<>();
    private DeepMap<Integer, Integer, Integer, Double> moneySavedMap;// = new DeepMap<>();

    public DatabaseUser(){}

    public DatabaseUser(String email){
        this.email = email;
        this.currentDistance = 0;
        this.co2CurrentMonth = 0;
        this.co2CurrentYear = 0;
        this.co2Tot = 30;
        this.firstUse = true;
        co2SavedMap = new DeepMap<>();
        moneySavedMap = new DeepMap<>();
        oldCo2Json = "";
        oldMoneyJson = "";

        updateMoneyJson();
        updateCo2Json();

        this.company = "";
    }

    public DatabaseUser(String email, String name){
        this(email);
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String connectedCompany) {
        this.company = connectedCompany;
    }

    public double getCurrentDistance() {
        return currentDistance;
    }

    public void setCurrentDistance(double currentDistance) {
        this.currentDistance = currentDistance;
    }

    public long getTimeStampInMillis() {
        return timeStampInMillis;
    }

    public void setTimeStampInMillis(long timeStampInMillis) {
        this.timeStampInMillis = timeStampInMillis;
    }

    public boolean isFirstUse() {
        return firstUse;
    }

    public void setFirstUse(boolean firstUse) {
        this.firstUse = firstUse;
    }

    public double getCo2CurrentMonth() {
        return co2CurrentMonth;
    }

    public void setCo2CurrentMonth(double co2CurrentMonth) {
        this.co2CurrentMonth = co2CurrentMonth;
    }

    public double getCo2CurrentYear() {
        return co2CurrentYear;
    }

    public void setCo2CurrentYear(double co2CurrentYear) {
        this.co2CurrentYear = co2CurrentYear;
    }

    public double getCo2Tot() {
        return co2Tot;
    }

    public void setCo2Tot(double co2Tot) {
        this.co2Tot = co2Tot;
    }

    public String getCo2Json() {
        return co2Json;
    }

    public String getMoneyJson() {
        return moneyJson;
    }

    /**
     *
     * @param avoidDatabaseUpload a parameter that can be both true or false, avoids member array to
     *                             be stored at the database
     * @return DeepMap of the co2 the user have saved
     */
    public DeepMap<Integer, Integer, Integer, Double> getCo2SavedMap(boolean avoidDatabaseUpload) {
        updateCo2Map();
        return co2SavedMap;
    }

    public void setCo2SavedMap(DeepMap<Integer, Integer, Integer, Double> co2SavedMap) {
        this.co2SavedMap = co2SavedMap;
        updateCo2Json();
    }

    /**
     *
     * @param avoidDatabaseUpload a parameter that can be both true or false, avoids member array to
     *                             be stored at the database
     * @return DeepMap of the money the user have saved
     */
    public DeepMap<Integer, Integer, Integer, Double> getMoneySavedMap(boolean avoidDatabaseUpload) {
        updateMoneyMap();
        return moneySavedMap;
    }

    public void setMoneySavedMap(DeepMap<Integer, Integer, Integer, Double> moneySavedMap) {
        this.moneySavedMap = moneySavedMap;
        updateMoneyJson();
    }

    private void updateCo2Json(){
        Gson gson = new Gson();
        co2Json =  gson.toJson(co2SavedMap);
    }

    private void updateMoneyJson(){
        Gson gson = new Gson();
        moneyJson =  gson.toJson(moneySavedMap);
    }

    private void updateMoneyMap(){
        if(moneySavedMap == null || !oldMoneyJson.equals(moneyJson)){
            Gson gson = new Gson();
            moneySavedMap = gson.fromJson(this.getMoneyJson(), DeepMap.class);
            oldMoneyJson = this.getMoneyJson();
        }
    }

    private void updateCo2Map(){
        if(co2SavedMap == null || !oldCo2Json.equals(co2Json)){
            Gson gson = new Gson();
            co2SavedMap = gson.fromJson(this.getCo2Json(), DeepMap.class);
            oldCo2Json = this.getCo2Json();
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "moneyJson='" + moneyJson + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", currentDistance=" + currentDistance +
                ", timeStampInMillis=" + timeStampInMillis +
                ", firstUse=" + firstUse +
                ", co2CurrentMonth=" + co2CurrentMonth +
                ", co2CurrentYear=" + co2CurrentYear +
                ", co2Tot=" + co2Tot +
                ", co2Json='" + co2Json + '\'' +
                '}';
    }
}
