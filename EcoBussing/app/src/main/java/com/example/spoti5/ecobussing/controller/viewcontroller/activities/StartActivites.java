package com.example.spoti5.ecobussing.controller.viewcontroller.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import com.example.spoti5.ecobussing.controller.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.controller.SaveHandler;

/**
 * Created by Erik on 2015-09-27.
 * First class that is initizated. Works like a factory, chooses
 * what activity should be started. If no user i logged in, start register activity. If
 * user is logged in but hasn't traveled since last use, start main activity. If the user has traveled,
 * start overview activity
 */
public class StartActivites extends ActivityController{

    private IUser loggedUser;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedUser = SaveHandler.getCurrentUser();

        if(playerIsLogged()){
            if (loggedUser.getCurrentDistance() > 0) {
                startOverviewActivity();
            } else {
                startMainActivity();
            }
        } else {
            startRegisterActivity();
        }
    }

    private boolean playerIsLogged(){
        return loggedUser != null;
    }


}
