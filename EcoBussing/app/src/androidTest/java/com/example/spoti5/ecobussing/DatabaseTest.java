package com.example.spoti5.ecobussing;

import android.test.AndroidTestCase;

import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.model.ErrorCodes;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabaseConnected;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.controller.profile.User;

import java.util.List;

/**
 * Created by Erik on 2015-10-07.
 */
public class DatabaseTest extends AndroidTestCase implements IDatabaseConnected {
    private User user;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private IDatabase database;

    private List<IUser> allUsers;
    private List<IUser> topList;

    public DatabaseTest(){
        database = DatabaseHolder.getDatabase();
        user = new User("hejhej001@mail.com", "stefan");
        user2 = new User("hejhej002@mail.com", "stefan");
        user3 = new User("hejhej003@mail.com", "stefan");
        user4 = new User("hejhej004@mail.com", "stefan");
        user5 = new User("hejhej005@mail.com", "stefan");

        /*
        user.setCO2Saved(2.0);
        user2.setCO2Saved(1.5);
        user3.setCO2Saved(2.0);
        user4.setCO2Saved(3.1);
        user5.setCO2Saved(0.2);
        */
        database.addUser(user.getEmail(), "Zzzzzzz", user, this);
        database.addUser(user2.getEmail(), "Zzzzzzz", user, this);
        database.addUser(user3.getEmail(), "Zzzzzzz", user, this);
        database.addUser(user4.getEmail(), "Zzzzzzz", user, this);
        database.addUser(user5.getEmail(), "Zzzzzzz", user, this);


    }

    int numberOfUsers = 0;
    @Override
    public void addingFinished() {
        int error = database.getErrorCode();
        if(error == ErrorCodes.NO_ERROR){
            System.out.println("----------------------");
            System.out.println("User added");
            numberOfUsers++;
            if(numberOfUsers == 4){
                getLists();
            }
        } else if(error == ErrorCodes.BAD_EMAIL){
            System.out.println("----------------------");
            System.out.println("User already exists");
            numberOfUsers++;
            if(numberOfUsers == 4){
                getLists();
            }
        } else if(error == ErrorCodes.NO_CONNECTION){
            System.out.println("----------------------");
            System.out.println("No connection");
            System.out.println("----------------------");
        } else if(error == ErrorCodes.UNKNOWN_ERROR){
            System.out.println("----------------------");
            System.out.println("Unkown error");
            System.out.println("----------------------");
        } else {
            System.out.println("----------------------");
            System.out.println("!!Unkown situation, this should not happen!!");
            System.out.println("----------------------");
        }
    }

    private void getLists(){
        allUsers = database.getUsers();
        topList = database.getUserToplistAll();

        printLists();
    }

    private void printLists(){
        for(IUser u: allUsers){
            System.out.println("----------------------");
            System.out.println("User " + u.getName());
        }
        for (IUser u: topList){
            System.out.println("----------------------");
            System.out.println(u.getCO2Saved());
        }
        System.out.println("----------------------");
    }

    @Override
    public void loginFinished() {
        //doesn't test this yet
    }

}
