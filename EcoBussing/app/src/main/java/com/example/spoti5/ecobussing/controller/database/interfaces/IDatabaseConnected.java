package com.example.spoti5.ecobussing.controller.database.interfaces;

/**
 * Created by Erik on 2015-09-30.
 */
public interface IDatabaseConnected {
    /**
     * Called when something has been added, or failed to be added, to the database
     */
    void addingFinished();

    /**
     * Called when a log in has succeeded or failed on the database
     */
    void loginFinished();
}
