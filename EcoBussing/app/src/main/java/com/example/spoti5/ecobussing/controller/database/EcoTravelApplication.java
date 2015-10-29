package com.example.spoti5.ecobussing.controller.database;

import com.firebase.client.Firebase;

/**
 * Created by matildahorppu on 29/09/15.
 * Creates a context for the database class
 */
public class EcoTravelApplication extends android.app.Application {

    // Context to the database
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
