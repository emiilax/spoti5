package com.example.spoti5.ecobussing.controller.listeners;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.StrictMode;

import com.example.spoti5.ecobussing.controller.apirequest.BusConnection;
import com.example.spoti5.ecobussing.model.bus.Bus;
import com.example.spoti5.ecobussing.model.bus.Busses;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Emil Axelsson on 30/09/15.
 *
 * Singelton class. Used to check the network connection. React when network state change
 *
 */
public class NetworkStateChangeReciever extends BroadcastReceiver implements Runnable {

    private static NetworkStateChangeReciever theReciver = null;
    private String bssid;
    private BusConnection busConnection;
    private boolean onBus = false;
    private boolean connectedToWifi = false;
    private Activity activity;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean netwCon = false;
    private boolean stopThread;

    private NetworkStateChangeReciever(){
        busConnection = new BusConnection();
        this.addPropertyChangeListener(busConnection);
        netwCon = hasActiveInternetConnection();
    }

    /**
     * Get the NetworkStateChangeReciver object
     *
     * @return theReceiver
     */
    public static NetworkStateChangeReciever getInstance(){

        if(theReciver == null){
            theReciver = new NetworkStateChangeReciever();
        }

        return theReciver;

    }

    /**
     * Check if there really is a network connection. "Pings" google, and check if the responsecode
     * is right.
     *
     * @return a boolean whether its connected to internet or not
     */
    public boolean hasActiveInternetConnection(){

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

    /**
     * Sets the netwCon. And if its a network connection see if a journey should e started
     * @param truFal if it is networkconnection or not
     */
    public void setNetwCon(boolean truFal){
        netwCon = truFal;

        if(netwCon) {
            tryStartJourney();

            // The busconnection now know that there is network connection.
            pcs.firePropertyChange("netwConnected", null, null);
        }
    }


    /**
     * Updates the network connection.
     *
     * @param intent the intent
     * @param context the context
     */
    public void updateConnect(Intent intent, Context context){

        NetworkInfo in = (NetworkInfo)intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);



        if(in!=null && in.getState() == NetworkInfo.State.CONNECTED) {

            setNetwCon(hasActiveInternetConnection());

            // It can take som seconds to get active networkconnection after a state-change.
            // Therefore a thread is started where it checks the active-networkconnectiom for
            // 10 seconds.
            // Just because you are connected to network, does not necessary mean that you have
            // active-networkconnection.
            if(!netwCon){
                stopThread = false;
                new Thread(this).start();
            }

        }else{
            stopThread = true;
            netwCon = false;
        }

    }

    // Called whenever the network state changes.
    @Override
    public void onReceive(Context context, Intent intent) {

        //
        updateConnect(intent, context);

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null ) {

            if(info.isConnected()) {
                // Used to get extra info about the wificonnection
                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();


                if(wifiInfo.getBSSID() != null){
                    connectedToWifi = true;
                    bssid = wifiInfo.getBSSID().replace(":", "");

                    // Used in develop-mode
                    this.pcs.firePropertyChange("connected", null, bssid);

                    tryStartJourney();
                }

                // if there is no network connection
            } else {


                connectedToWifi = false;


                // Used in develop-mode
                this.pcs.firePropertyChange("disconnected", null, null);

                tryEndJourney();
            }
        }
    }

    /**
     * Called when the network connection is lost. Calls the busconnection endJourney if
     * the user was on a bus before the connection were lost
     */
    public void tryEndJourney(){
        if(onBus){
            onBus = false;
            try{
                busConnection.endJourney();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts the journey if all of the requirements are filled.
     * (Network-, wifi-connection. User not on bus, and the mac-address matches with bus)
     */
    public void tryStartJourney(){
        System.out.println("onbus: " + onBus+  ", wifi: " + connectedToWifi + " ,network: " + netwCon);
        if(!onBus && connectedToWifi && netwCon){

            for(Bus b: Busses.theBusses){
                //System.out.println("Bssid: " + bssid + "bus: " + b.getMacAdress());
                if(bssid.equals(b.getMacAdress())){
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

    // Getters
    public String getBssid(){ return bssid; }

    public boolean isConnectedToWifi(){ return connectedToWifi; }

    public boolean isNetwConnected(){ return netwCon; }


    /**
     * Adds a listener
     * @param listener, the class that you add as a listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    /**
     * Removes a listener
     * @param listener, the class that you remove as a listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }




    // Used to check active-network connection. Check every second for 10 seconds.
    @Override
    public void run() {
        int i = 0;

        while(!netwCon && !stopThread && i < 10 ){

            setNetwCon(hasActiveInternetConnection());
            //System.out.println("Connected: " + netwCon);

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
