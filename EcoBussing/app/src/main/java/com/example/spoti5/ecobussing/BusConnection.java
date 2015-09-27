package com.example.spoti5.ecobussing;





import android.util.JsonReader;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by emilaxelsson on 24/09/15.
 */
public class BusConnection {

    public static void main(String[] args) {

        BusConnection demo = new BusConnection();
        try {
            demo.doGet();
        } catch (IOException e) {
        }

    }

    private void doGet() throws IOException {
        long t2 = System.currentTimeMillis();
        long t1 = t2 - (60 * 1000);

        StringBuffer response = new StringBuffer();
        //TODO Enter your base64 encoded Username:Password
        String key = "Z3JwMjY6REhrdUFQRlZuIw==";
        String url = "https://ece01.ericsson.net:4443/ecity?dgw=Ericsson$Vin_Num_001&sensorSpec=Ericsson$Next_Stop&t1="
                + t1 + "&t2=" + t2;

        URL requestURL = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) requestURL
                .openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization","Basic "+ key);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));

        String inputLine;

        //InputStream inp =


        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //TestGson testGson = new Gson().fromJson(response.toString(), TestGson.class);
       // new Gson().fromJson(response.toString(), )

        //JsonReader jsonReader = new JsonReader(new InputStreamReader(
         //       con.getInputStream(), "UTF-8"));
//        System.out.println(testGson.toString());
        //response.delete(0,1);
        //response.delete(response.length()-1, response.length());

        String str = response.toString();
        str = str.replace('\"','\'');
        str = str.substring(1,118);

        //response.delete(0, 1);
        //response.delete(response.length() - 1, response.length());
        System.out.println(str);
        System.out.println(response.toString());
        TestGson testGson = new Gson().fromJson(str, TestGson.class);

        System.out.println(testGson);

    }

    private void replaceAll(String r, String c, String string){
        for(int i = 0; i < string.length(); i++){



        }
    }

    //private String getNameOfNextStation(){

    //}
}
