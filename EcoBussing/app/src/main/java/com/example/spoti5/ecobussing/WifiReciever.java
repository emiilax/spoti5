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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.List;

/**
 * Created by emilaxelsson on 30/09/15.
 */

public class WifiReciever extends BroadcastReceiver {

    private static WifiReciever theReciver = null;
    private String bssid;
    private Bus theBus;
    private BusConnection busConnection;
    private boolean onBus = false;
    private boolean connectedToWifi = false;
    private Activity activity;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


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
                    this.pcs.firePropertyChange("connected", null, bssid);


                    for(Bus b: Busses.theBusses){
                        if(bssid.equals(b.getMacAdress())){
                            onBus = true;
                            try {
                                busConnection.beginJourey(b);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;

                        }
                    }


                }
                connectedToWifi = true;

            } else{
                connectedToWifi = false;
                this.pcs.firePropertyChange("disconnected", null, null);

                if(onBus){
                    try {
                        busConnection.endJourney();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    onBus = false;
                }
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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public String getBssid(){
        return bssid;
    }

    public boolean isConnectedToWifi(){
        return connectedToWifi;
    }
}
