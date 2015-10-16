package com.example.spoti5.ecobussing.Activites;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.example.spoti5.ecobussing.Database.Database;
import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.ErrorCodes;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Database.IDatabaseConnected;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

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

        if(!playerIsLogged()){
            startOverviewActivity();
        } else {
            startRegisterActivity();
        }
    }

    private boolean playerIsLogged(){
        return loggedUser != null;
    }


}
