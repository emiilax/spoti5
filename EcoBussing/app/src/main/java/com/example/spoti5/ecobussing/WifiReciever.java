package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.example.spoti5.ecobussing.BusData.Bus;
import com.example.spoti5.ecobussing.BusData.Busses;

import java.util.List;

/**
 * Created by emilaxelsson on 30/09/15.
 */
public class WifiReciever extends BroadcastReceiver {

    private static WifiReciever theReciver = null;
    private String bssid;
    private Bus theBus;
    private BusConnection busConnection;
    private boolean onBus;
    private Activity activity;


    private WifiReciever(Activity activity){
        busConnection = new BusConnection();
        this.activity = activity;

    }

    public static WifiReciever getInstance(Activity activity){

        if(theReciver == null){
            theReciver = new WifiReciever(activity);
        }

        return theReciver;

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //System.out.println(" internet event");
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null) {
            if(info.isConnected()) {
                // Do your work.

                // e.g. To check the Network Name or other info:
                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if(wifiInfo.getBSSID() != null){
                    bssid = wifiInfo.getBSSID().replace(":", "");
                    System.out.println("Connected");

                    /*
                    try{
                        ((MainActivity)activity).setConnected(bssid);
                    } catch (Exception e){

                    }
                    */

                }


                System.out.println("Connected");
                System.out.println(bssid);

                //if(onTheBus() && !onBus){
                 //   onBus = true;
                    //busConnection.beginJourney(theBus);
                //}
            } else{
                /*try{
                    ((MainActivity)activity).setDisconnected();
                } catch (Exception e){

                }*/
            }
        }
    }

    public boolean onTheBus(){
        List<Bus> busses = Busses.theBusses;

        for(Bus b: busses){
            if(b.getMacAdress().equals(bssid)){
                theBus = b;
                return true;
            }
        }
        return false;


    }

    public String getBssid(){
        return bssid;
    }
}
