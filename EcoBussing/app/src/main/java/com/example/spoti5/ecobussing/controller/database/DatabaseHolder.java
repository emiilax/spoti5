package com.example.spoti5.ecobussing.controller.database;

import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.io.net.Database;

/**
 * This class makes the database easily exchangeable, aslong as
 * the database class implements IDatabase. When retriving the database this
 * class must be used
 * Created by erikk on 2015-09-23.
 */
public class DatabaseHolder {
    private static IDatabase database;

    public static IDatabase getDatabase(){
        if(database == null){
            database = new Database();
            return database;
        } else {
            return database;
        }
    }
}
