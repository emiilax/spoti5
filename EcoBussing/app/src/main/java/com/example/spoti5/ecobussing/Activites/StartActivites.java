package com.example.spoti5.ecobussing.Activites;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

/**
 * Created by Erik on 2015-09-27.
 */
public class StartActivites extends ActivityController {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(playerIsLogged()){
            startOverviewActivity();
        } else {
            startRegisterActivity();
        }
    }

    private boolean playerIsLogged(){
        return false;
    }
}
