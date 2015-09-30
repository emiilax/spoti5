package com.example.spoti5.ecobussing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * Created by emilaxelsson on 30/09/15.
 */
public class WifiReciever extends BroadcastReceiver {

    private static WifiReciever theReciver = null;
    private String ssid;
    private Bus theBus;
    private BusConnection busConnection;
    private boolean onBus;


    private WifiReciever(){
        busConnection = new BusConnection();

    }

    public static WifiReciever getInstance(){

        if(theReciver == null){
            theReciver = new WifiReciever();
        }

        return theReciver;

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null) {
            if(info.isConnected()) {
                // Do your work.

                // e.g. To check the Network Name or other info:
                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                ssid = wifiInfo.getSSID().replace(":", "");

                if(onTheBus() && !onBus){
                    onBus = true;
                    //busConnection.beginJourney(theBus);
                }
            }
        }
    }

    public boolean onTheBus(){
        List<Bus> busses = Busses.theBusses;

        for(Bus b: busses){
            if(b.getMacAdress().equals(ssid)){
                theBus = b;
                return true;
            }
        }
        return false;


    }

    public String getSsid(){
        return ssid;
    }
}
