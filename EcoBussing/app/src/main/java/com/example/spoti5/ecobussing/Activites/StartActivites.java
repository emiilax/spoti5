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
public class StartActivites extends ActivityController implements IDatabaseConnected {

    private static Context context;
    private IUser loggedUser;
    private String password;
    private IDatabase database;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        loggedUser = SaveHandler.getCurrentUser();
        password = SaveHandler.getPassword();
        database = DatabaseHolder.getDatabase();
        logPlayer();
    }

    private void logPlayer(){
        database.loginUser(loggedUser.getEmail(), password, this);
    }

    @Override
    public void addingUserFinished() {
        //never used
    }

    @Override
    public void loginFinished() {
        if(database.getErrorCode() == ErrorCodes.NO_ERROR && false){
            startOverviewActivity();
        } else {
            startRegisterActivity();
        }
    }

    public static Context getContext(){
        return context;
    }
}
