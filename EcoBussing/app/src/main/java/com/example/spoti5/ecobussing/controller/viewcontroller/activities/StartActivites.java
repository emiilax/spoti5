package com.example.spoti5.ecobussing.controller.viewcontroller.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.controller.SaveHandler;

/**
 * Created by Erik on 2015-09-27.
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
