package com.example.spoti5.ecobussing.model.profile;

import com.example.spoti5.ecobussing.controller.calculations.Calculator;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;

import java.util.Calendar;

/**
 * Created by erikk on 2015-09-16. This class is editable and will not change number of nodes
 * or similar on the database
 */
public class User implements IUser {

    private static final Calendar calendar = Calendar.getInstance();

    private Integer stampedMonth;
    private Integer stampedYear;
    private Integer savedMonth;
    private Integer savedYear;
    private DatabaseUser dbUser;

    public User(){}


    /**
     * Creates a new user from an existing database user
     * @param dbUser already existing database user
     */
    public User(DatabaseUser dbUser){
        this.dbUser = dbUser;
    }

    /**
     * Creates a user with username, email and password. This class does not check
     * username and password
     * @param email Has to cbe checked before entered here
     */
    public User(String email){
        dbUser = new DatabaseUser(email);
    }

    public User(String email, String name){
        dbUser = new DatabaseUser(email, name);
    }


    @Override
    public void setName(String name) {
        dbUser.setName(name);
    }

    @Override
    public String getName() {
        return dbUser.getName();
    }

    @Override
    public String getEmail() {
        return dbUser.getEmail();
    }

    @Override
    public double getCurrentDistance() {
        return dbUser.getCurrentDistance();
    }

    @Override
    public void resetCurrentDistance() {
        dbUser.setCurrentDistance(0);
    }

    @Override
    public void incCurrentDistance(double addedDistance) {
        if (addedDistance > 0) {
            dbUser.setCurrentDistance(dbUser.getCurrentDistance() + addedDistance);
        }
    }

    @Override
    public void setCompany(String name) {
        dbUser.setCompany(name);
    }

    @Override
    public String getCompany() {
        return dbUser.getCompany();
    }

    /**
     * Calculates the co2 saved from the distance and adds it into the current dates position in the DeepMap.
     * @param distance the distance to be calculated into CO2 saved and added.
     */
    @Override
    public void incCO2Saved(double distance) {
        double co2Saved = Calculator.getCalculator().calculateCarbonSaved(distance);
        DeepMap<Integer, Integer, Integer, Double> map = dbUser.getCo2SavedMap(true);
        map.addToCurrentDate(co2Saved);
        dbUser.setCo2SavedMap(map);

        if(!dbUser.getCompany().equals("")){
            updateCompany(co2Saved);
        }

        this.incCurrentDistance(distance);
        this.addToCurrentCO2Saved(co2Saved);
    }

    //This method is used in testing for adding values to different dates that are not today
    public void updateSpecDate(double distance,int year, int month, int day){

        double co2Saved = Calculator.getCalculator().calculateCarbonSaved(distance);
        DeepMap<Integer, Integer, Integer, Double> map = dbUser.getCo2SavedMap(true);

        map.addToSpecificDate(year, month, day, co2Saved);
        dbUser.setCo2SavedMap(map);

        this.incCurrentDistance(distance);
        dbUser.setCo2Tot(dbUser.getCo2Tot() + co2Saved);

    }

    public void updateCompany(double distance){
        IProfile company = DatabaseHolder.getDatabase().getCompany(dbUser.getCompany());
        ((Company)company).newJourney(distance);
    }

    public void newJourney(double distance){
        incCO2Saved(distance);
        incMoneySaved(distance);

    }

    @Override
    public Double getCO2Saved() {
        return dbUser.getCo2SavedMap(true).getSumOfAllDates();
    }

    @Override
    public Double getCO2SavedYear(Integer year) {
        return dbUser.getCo2SavedMap(true).getSumOfOneYear(year);
    }

    @Override
    public Double getCO2SavedMonth(Integer year, Integer month) {
        return dbUser.getCo2SavedMap(true).getSumOfOneMonth(year, month);
    }

    @Override
    public Double getCO2SavedDate(Integer year, Integer month, Integer day) {
        return dbUser.getCo2SavedMap(true).getSpecificDate(year, month, day);
    }

    @Override
    public Double getCO2SavedPast7Days() {
        return dbUser.getCo2SavedMap(true).getSumOfPastSevenDays();
    }

    /**
     * Helpmethod to the incCO2Saved method.
     * Adds the saved CO2 to the CO2CurrentMonth, CO2CurrentYear and CO2Tot variables.
     * @param co2Saved the CO2 to be added to the variables.
     */
    private void addToCurrentCO2Saved(double co2Saved) {
        long timeStampInMillis = calendar.getTimeInMillis();
        dbUser.setTimeStampInMillis(timeStampInMillis);
        calendar.setTimeInMillis(timeStampInMillis);
        stampedMonth = calendar.get(Calendar.MONTH) + 1;
        stampedYear = calendar.get(Calendar.YEAR);

        if (dbUser.isFirstUse()) {
            savedMonth = stampedMonth;
            savedYear = stampedYear;
            dbUser.setFirstUse(false);
        }

        if (!stampedYear.equals(savedYear)) {
            dbUser.setCo2CurrentYear(0);
            dbUser.setCo2CurrentMonth(0);
            savedYear = stampedYear;
            savedMonth = stampedMonth;
        } else {
            if (!stampedMonth.equals(savedMonth)) {
                dbUser.setCo2CurrentMonth(0);
                savedMonth = stampedMonth;
            }
        }
        dbUser.setCo2CurrentMonth(dbUser.getCo2CurrentMonth() + co2Saved);
        dbUser.setCo2CurrentYear(dbUser.getCo2CurrentYear() + co2Saved);
        dbUser.setCo2Tot(dbUser.getCo2Tot() + co2Saved);
    }

    public double getCo2CurrentYear() {
        return dbUser.getCo2CurrentYear();
    }

    public double getCo2CurrentMonth() {
        return dbUser.getCo2CurrentMonth();
    }

    public double getCo2Tot() {
        return dbUser.getCo2Tot();
    }


    /**
     * Calculates the money saved from the distance and adds it into the current dates position in the DeepHashmap.
     * @param distance the distance to be calculated into money saved and added.
     */
    @Override
    public void incMoneySaved(double distance) {
        double moneySaved = Calculator.getCalculator().calculateMoneySaved(distance);
        DeepMap<Integer, Integer, Integer, Double> map = dbUser.getMoneySavedMap(true);
        map.addToCurrentDate(moneySaved);
        dbUser.setMoneySavedMap(map);
    }

    @Override
    public Double getMoneySaved() {
        return dbUser.getMoneySavedMap(true).getSumOfAllDates();
    }

    @Override
    public Double getMoneySavedYear(Integer year) {
        return dbUser.getMoneySavedMap(true).getSumOfOneYear(year);
    }

    @Override
    public Double getMoneySavedMonth(Integer year, Integer month) {
        return dbUser.getMoneySavedMap(true).getSumOfOneMonth(year, month);
    }

    @Override
    public Double getMoneySavedDate(Integer year, Integer month, Integer day) {
        return dbUser.getMoneySavedMap(true).getSpecificDate(year, month, day);
    }

    @Override
    public Double getMoneySavedPast7Days() {
        return dbUser.getMoneySavedMap(true).getSumOfPastSevenDays();
    }

    @Override
    public int getTotaltTimesTraveled() {
        return dbUser.getCo2SavedMap(true).getTotaltTimesTraveled();
    }

    @Override
    public DatabaseUser getDatabaseUser() {
        return dbUser;
    }

    @Override
    public Double getDistanceTraveled() {
        return dbUser.getCurrentDistance();
    }


    public long getTimeStampInMillis() {
        return dbUser.getTimeStampInMillis();
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
        return dbUser.isFirstUse();
    }


}
