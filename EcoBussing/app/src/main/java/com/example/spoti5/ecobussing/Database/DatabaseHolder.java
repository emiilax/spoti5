package com.example.spoti5.ecobussing.Database;

/**
 * Created by erikk on 2015-09-23.
 */
public class DatabaseHolder {
    private static IDatabase database;

    public static IDatabase getDatabase(){
        if(database == null){
            database = new TmpDatabase();
            return database;
        } else {
            return database;
        }
    }
}
