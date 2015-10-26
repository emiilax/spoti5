package com.example.spoti5.ecobussing;

import com.firebase.client.Firebase;

/**
 * Created by matildahorppu on 29/09/15.
 */
public class EcoTravelApplication extends android.app.Application {

    // Context to the database
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
