package com.example.spoti5.ecobussing.Profiles;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hilden on 2015-10-07.
 */

public class DeepMap<K1, K2, K3, V> implements Map<K1, Map<K2, Map<K3, V>>> {

    private Map<Integer, Map<Integer, Map<Integer, Double>>> underlyingMap = new HashMap<>();

    private Map<Integer, Map<Integer, Double>> tempMap1 = new HashMap<>();

    private Map<Integer, Double> tempMap2 = new HashMap<>();

    private Calendar calendar = Calendar.getInstance();
    private long timeStampInMillis = 0;
    private Integer stampedDay = new Integer(0);
    private Integer stampedMonth = new Integer(0);
    private Integer stampedYear = new Integer(0);


    /**
     * Inits tempmaps and underlying map, has to be done in order to be created as a Json object
     */
    public DeepMap() {
        tempMap2.put(0,0.0);
        tempMap1.put(0, tempMap2);
        underlyingMap.put(0, tempMap1);

    }

    /**
     * Retrieves a value from a specific date.
     * @param key1 the key for the outer map (the year).
     * @param key2 the key for the middle map (the month).
     * @param key3 the key for the inner map (the day).
     * @return the inner value (the specific date), or null if none exists.
     */
    public Double getSpecificDate(Integer key1, Integer key2, Integer key3) {
        tempMap1 = underlyingMap.get(key1);
        if (tempMap1 == null) {
            return null;
        }
        tempMap2 = tempMap1.get(key2);
        return tempMap2 == null ? null : tempMap2.get(key3);
    }

    /**
     * Puts a value in the specific date, removes the old value if there was one.
     * @param key1 the key for the outer map (the year).
     * @param key2 the key for the middle map (the month).
     * @param key3 the key for the inner map (the day).
     * @param value the value to put inside the specific date.
     * @return the former value, or null if none exists.
     */
    public Double setSpecificDate(Integer key1, Integer key2, Integer key3, Double value) {
        tempMap1 = underlyingMap.get(key1);
        if (tempMap1 == null) {
            tempMap1 = new HashMap<Integer, Map<Integer, Double>>();
            underlyingMap.put(key1, tempMap1);
            tempMap2 = new HashMap<Integer, Double>();
            tempMap1.put(key2, tempMap2);
        } else {
            tempMap2 = tempMap1.get(key2);
            if (tempMap2 == null) {
                tempMap2 = new HashMap<Integer, Double>();
                tempMap1.put(key2, tempMap2);
            }
        }
        return tempMap2.put(key3, value);
    }

    /**
     * Adds the value to the value already in this specific date.
     * @param key1 the key for the outer map (the year).
     * @param key2 the key for the middle map (the month).
     * @param key3 the key for the inner map (the day).
     * @param value the value to add to the old value in this specific date.
     * @return the added values.
     */
    public Double addToSpecificDate(Integer key1, Integer key2, Integer key3, Double value) {
        double tempDouble = 0;
        if (this.getSpecificDate(key1, key2, key3) != null) {
            tempDouble = this.getSpecificDate(key1, key2, key3);
        }
        tempDouble = tempDouble + value;
        this.setSpecificDate(key1, key2, key3, tempDouble);
        return tempDouble;
    }

    /**
     * Adds the value to the value already in the spot for the todays date.
     * (The key for the added value becomes the current date, eg. key1 = 2015, key2 = 2, key3 = 24)
     * @param value the value to add to the current date.
     * @return the sum.
     */
    public Double addToCurrentDate(Double value) {
        timeStampInMillis = calendar.getTimeInMillis();
        calendar.setTimeInMillis(timeStampInMillis);
        stampedDay = calendar.get(Calendar.DAY_OF_MONTH);
        stampedMonth = calendar.get(Calendar.MONTH) + 1;
        stampedYear = calendar.get(Calendar.YEAR);
        return this.addToSpecificDate(stampedYear, stampedMonth, stampedDay, value);
    }

    /**
     * Returns the value found in the spot for todays date.
     * @return todays date's value.
     */
    public Double getFromCurrentDate() {
        timeStampInMillis = calendar.getTimeInMillis();
        calendar.setTimeInMillis(timeStampInMillis);
        stampedDay = calendar.get(Calendar.DAY_OF_MONTH);
        stampedMonth = calendar.get(Calendar.MONTH) + 1;
        stampedYear = calendar.get(Calendar.YEAR);
        return getSpecificDate(stampedYear, stampedMonth, stampedDay);
    }

    /**
     * Adds all the values of all keys that make out dates.
     * @return the sum.
     */
    public Double getSumOfAllDates() {
        double sum = 0;
        for (int i = 0; i < 100; i++) {
            if (underlyingMap.get(2015+i) != null) {
                tempMap1 = underlyingMap.get(2015 + i);
                for (int j = 1; j < 13; j++) {
                    if (tempMap1.get(j) != null) {
                        tempMap2 = tempMap1.get(j);
                        for (int k = 1; k < 32; k++) {
                            if (tempMap2.get(k) != null) {
                                sum = sum + tempMap2.get(k);
                            }
                        }
                    }
                }
            }
        }
        return sum;
    }

    /**
     * Adds all the values of keys that make out dates in one specific year.
     * @param key1 the key to the specific outer map (the year) to sum up.
     * @return the sum.
     */
    public Double getSumOfOneYear(Integer key1) {
        double sum = 0;
        if (underlyingMap.get(key1) != null) {
            tempMap1 = underlyingMap.get(key1);
            for (int j = 1; j < 13; j++) {
                if (tempMap1.get(j) != null) {
                    tempMap2 = tempMap1.get(j);
                    for (int k = 1; k < 32; k++) {
                        if (tempMap2.get(k) != null) {
                            sum = sum + tempMap2.get(k);
                        }
                    }
                }
            }
        }
        return sum;
    }

    /**
     * Adds all the values of keys that make out dates in one specific year and month.
     * @param key1 the key to the specific outer map (the year).
     * @param key2 the key to the specific middle map (the month) in the specific outer map.
     * @return the sum.
     */
    public Double getSumOfOneMonth(Integer key1, Integer key2) {
        double sum = 0;
        if (underlyingMap.get(key1) != null) {
            tempMap1 = underlyingMap.get(key1);
            if (tempMap1.get(key2) != null) {
                tempMap2 = tempMap1.get(key2);
                for (int k = 1; k < 32; k++) {
                    if (tempMap2.get(k) != null) {
                        sum = sum + tempMap2.get(k);
                    }
                }
            }
        }
        return sum;
    }

    /**
     * Adds all the values of the keys that make out the past 7 days.
     * @return the sum.
     */
    public Double getSumOfPastSevenDays() {
        double sum = 0;
        timeStampInMillis = calendar.getTimeInMillis();
        calendar.setTimeInMillis(timeStampInMillis);
        stampedDay = calendar.get(Calendar.DAY_OF_MONTH);
        stampedMonth = calendar.get(Calendar.MONTH) + 1;
        stampedYear = calendar.get(Calendar.YEAR);
        for (int i = 0; i < 7; i++) {
            if (this.getSpecificDate(stampedYear, stampedMonth, stampedDay - i) != null) {
                sum = sum + this.getSpecificDate(stampedYear, stampedMonth, stampedDay - i);
            }
        }
        return sum;
    }

    @Override
    public void clear() {
        underlyingMap.clear();
    }

    @Override
    public int size() {
        return underlyingMap.size();
    }

    public int middleSize(K1 key1) {
        return underlyingMap.get(key1).size();
    }

    public int innerSize(K1 key1, K2 key2) {
        return underlyingMap.get(key1).get(key2).size();
    }










// --- NOT YET IMPLEMENTED METHODS ---

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @NonNull
    @Override
    public Set<Entry<K1, Map<K2, Map<K3, V>>>> entrySet() {
        return null;
    }

    @Override
    public Map<K2, Map<K3, V>> get(Object key) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NonNull
    @Override
    public Set<K1> keySet() {
        return null;
    }

    @Override
    public Map<K2, Map<K3, V>> put(K1 key, Map<K2, Map<K3, V>> value) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K1, ? extends Map<K2, Map<K3, V>>> map) {

    }

    @Override
    public Map<K2, Map<K3, V>> remove(Object key) {
        return null;
    }


    @NonNull
    @Override
    public Collection<Map<K2, Map<K3, V>>> values() {
        return null;
    }

    @Override
    public String toString() {
        return "DeepMap{" +
                "underlyingMap=" + underlyingMap +
                ", tempMap1=" + tempMap1 +
                ", tempMap2=" + tempMap2 +
                ", timeStampInMillis=" + timeStampInMillis +
                ", stampedDay=" + stampedDay +
                ", stampedMonth=" + stampedMonth +
                ", stampedYear=" + stampedYear +
                '}';
    }
}
