package com.example.spoti5.ecobussing.BusData;

import com.example.spoti5.ecobussing.JsonClasses.EA.EARespond;
import com.example.spoti5.ecobussing.JsonClasses.VA.StopLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by emilaxelsson on 07/10/15.
 *
 * Class that contains the busstops for busline nr55.
 *
 * This solution has been made because there
 * are no simple way to get all of a bus-lines stations/stops.
 *
 * This is only a temporary class
 *
 */
public class Bus55Stops {
     private static final String bus55Json =
            "{\"StopLocation\":[{\n" +
            "\"name\":\"Sven Hultins plats, Göteborg\",\n" +
            "\"id\":\"9022014006475001\",\n" +
            "\"lon\":\"11.976696\",\n" +
            "\"lat\":\"57.686350\",\n" +
            "\"track\":\"A\"\n" +
            "},{\n" +
            "\"name\":\"Chalmersplatsen, Göteborg\",\n" +
            "\"id\":\"9022014001961001\",\n" +
            "\"lon\":\"11.973469\",\n" +
            "\"lat\":\"57.689298\",\n" +
            "\"track\":\"A\"\n" +
            "},{\n" +
            "\"name\":\"Kapellplatsen, Göteborg\",\n" +
            "\"id\":\"9022014003760005\",\n" +
            "\"lon\":\"11.974072\",\n" +
            "\"lat\":\"57.693766\",\n" +
            "\"track\":\"E\"\n" +
            "},{\n" +
            "\"name\":\"Götaplatsen, Göteborg\",\n" +
            "\"id\":\"9022014003020001\",\n" +
            "\"lon\":\"11.979043\",\n" +
            "\"lat\":\"57.697721\",\n" +
            "\"track\":\"A\"\n" +
            "},{\n" +
            "\"name\":\"Valand, Göteborg\",\n" +
            "\"id\":\"9022014007220003\",\n" +
            "\"lon\":\"11.974872\",\n" +
            "\"lat\":\"57.700490\",\n" +
            "\"track\":\"C\"\n" +
            "},{\n" +
            "\"name\":\"Kungsportsplatsen, Göteborg\",\n" +
            "\"id\":\"9022014004090003\",\n" +
            "\"lon\":\"11.969676\",\n" +
            "\"lat\":\"57.704220\",\n" +
            "\"track\":\"C\"\n" +
            "},{\n" +
            "\"name\":\"Brunnsparken, Göteborg\",\n" +
            "\"id\":\"9022014001760002\",\n" +
            "\"lon\":\"11.967671\",\n" +
            "\"lat\":\"57.707466\",\n" +
            "\"track\":\"B\"\n" +
            "},{\n" +
            "\"name\":\"Lilla Bommen, Göteborg\",\n" +
            "\"id\":\"9022014004380002\",\n" +
            "\"lon\":\"11.966691\",\n" +
            "\"lat\":\"57.709362\",\n" +
            "\"track\":\"B\"\n" +
            "},{\n" +
            "\"name\":\"Frihamnsporten, Göteborg\",\n" +
            "\"id\":\"9022014002472002\",\n" +
            "\"lon\":\"11.959051\",\n" +
            "\"lat\":\"57.718253\",\n" +
            "\"track\":\"B\"\n" +
            " },{\n" +
            "\"name\":\"Pumpgatan, Göteborg\",\n" +
            "\"id\":\"9022014005355002\",\n" +
            "\"lon\":\"11.946025\",\n" +
            "\"lat\":\"57.712688\",\n" +
            "\"track\":\"B\"\n" +
            "},{\n" +
            "\"name\":\"Regnbågsgatan, Göteborg\",\n" +
            "\"id\":\"9022014005465004\",\n" +
            "\"lon\":\"11.942708\",\n" +
            "\"lat\":\"57.710836\",\n" +
            "\"track\":\"D\"\n" +
            "},{\n" +
            "\"name\":\"Lindholmen, Göteborg\",\n" +
            "\"id\":\"9022014004490004\",\n" +
            "\"lon\":\"11.937944\",\n" +
            "\"lat\":\"57.708113\",\n" +
            "\"track\":\"D\"\n" +
            "},{\n" +
            "\"name\":\"Teknikgatan, Göteborg\",\n" +
            "\"id\":\"9022014006675001\",\n" +
            "\"lon\":\"11.937198\",\n" +
            "\"lat\":\"57.706917\",\n" +
            "\"track\":\"A\"\n" +
            "}]}";


       // Instanciate the class stoplist
       private static StopList stopList = new Gson().fromJson(bus55Json, StopList.class);

       // The list containing all the stops;
       public static List<StopLocation> stops = stopList.getStopLocation();



}