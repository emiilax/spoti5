package com.example.spoti5.ecobussing.Activites;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.spoti5.ecobussing.WifiReciever;

/**
 * Created by Erik on 2015-09-27.
 */
public abstract class ActivityController extends AppCompatActivity {



    // The variables used to react when connected to wifi
    protected WifiReciever wifiReciever = WifiReciever.getInstance(this);;
    protected IntentFilter intentFilter = new IntentFilter();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addWifiChangeHandler();

    }

    /**
     * Starts the overview activity. Is not reverseable
     */
    protected void startRegisterActivity() {
        //addWifiChangeHandler();

        Intent registerActivity = new Intent(ActivityController.this, RegisterActivity.class);
        registerActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityController.this.startActivity(registerActivity);
    }

    /**
     * Starts the login activity.
     */
    protected void startLoginActivity() {
        //addWifiChangeHandler();
        Intent loginActivity = new Intent(ActivityController.this, LoginActivity.class);
        ActivityController.this.startActivity(loginActivity);
    }

    /**
     * Starts the overview activity. Is not reverseable
     */
    protected void startOverviewActivity() {
        //addWifiChangeHandler();
        Intent overviewActivity = new Intent(ActivityController.this, OverviewActivity.class);
        overviewActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityController.this.startActivity(overviewActivity);
    }

    /**
     * Starts the overview activity.
     */
    protected void startMainActivity() {
        //addWifiChangeHandler();
        Intent MainActivity = new Intent(ActivityController.this, MainActivity.class);
        ActivityController.this.startActivity(MainActivity);
    }

    /**
     * Sets the sensor for whenever the wifi-state change
     */
    protected void addWifiChangeHandler(){
        //unregisterReceiver(wifiReciever);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(wifiReciever, intentFilter);
    }
}
