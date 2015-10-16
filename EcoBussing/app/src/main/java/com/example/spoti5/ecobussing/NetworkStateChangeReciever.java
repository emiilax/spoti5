package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.StrictMode;

import com.example.spoti5.ecobussing.APIRequest.BusConnection;
import com.example.spoti5.ecobussing.Activites.ActivityController;
import com.example.spoti5.ecobussing.BusData.Bus;
import com.example.spoti5.ecobussing.BusData.Busses;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by emilaxelsson on 30/09/15.
 *
 * Singelton class. As
 */

public class NetworkStateChangeReciever extends BroadcastReceiver implements Runnable {

    private static NetworkStateChangeReciever theReciver = null;
    private String bssid;
    private Bus theBus;
    private BusConnection busConnection;
    private boolean onBus = false;
    private boolean connectedToWifi = false;
    private Activity activity;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private boolean netwCon = false;

    private NetworkStateChangeReciever(){
        busConnection = new BusConnection();
        this.addPropertyChangeListener(busConnection);

        netwCon = hasActiveInternetConnection(ActivityController.getContext());
        System.out.println("Connected to network: " + netwCon);
    }



    public static NetworkStateChangeReciever getInstance(){

        if(theReciver == null){
            theReciver = new NetworkStateChangeReciever();
        }

        return theReciver;

    }


    public boolean hasActiveInternetConnection(Context context){

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);

            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(1500);
            urlc.connect();
            System.out.println(urlc.getResponseCode());
            return (urlc.getResponseCode() == 200);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

    public void setNetwCon(boolean truFal){
        netwCon = truFal;

        if(netwCon) {
            startJourney();
            pcs.firePropertyChange("netwConnected", null, null);
        }
    }

    public boolean isNetwConnected(){
        return netwCon;
    }

    Thread getConnectionThread = null;
    private boolean stopThread;

    public void updateConnect(Intent intent, Context context){

        NetworkInfo in = (NetworkInfo)intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);

        System.out.println("State: " + in.getState());

        if(in!=null && in.getState() == NetworkInfo.State.CONNECTED) {

            setNetwCon(hasActiveInternetConnection(context));

            if(!netwCon){
                stopThread = false;
                new Thread(this).start();
            }

        }else{
            stopThread = true;
            netwCon = false;
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //System.out.println(" internet event");


        updateConnect(intent, context);
        //System.out.println("Connected to network: " + netwCon);

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null ) {

            if(info.isConnected()) {
                // Do your work.
                connectedToWifi = true;
                // e.g. To check the Network Name or other info:
                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if(wifiInfo.getBSSID() != null){
                    bssid = wifiInfo.getBSSID().replace(":", "");
                    //System.out.println("Connected");
                    this.pcs.firePropertyChange("connected", null, bssid);


                    for(Bus b: Busses.theBusses){
                        if(bssid.equals(b.getMacAdress()) && netwCon){
                            onBus = true;
                            System.out.println("on bus");
                            try {
                                busConnection.beginJourey(b);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;

                        }
                    }
                }


            } else {
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

    public void endJourney(){
        if(onBus){
            try{
                busConnection.endJourney();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void startJourney(){
        if(!onBus && connectedToWifi){
            for(Bus b: Busses.theBusses){
                if(bssid.equals(b.getMacAdress()) && netwCon){
                    onBus = true;
                    System.out.println("on bus");
                    try {
                        busConnection.beginJourey(b);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                }
            }
        }
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

    @Override
    public void run() {
        int i = 0;

        while(!netwCon && !stopThread && i < 10 ){

            setNetwCon(hasActiveInternetConnection(ActivityController.getContext()));
            System.out.println("Connected: " + netwCon);

            synchronized (this){
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
    }
}
