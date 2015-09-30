package com.example.spoti5.ecobussing.Database;

import com.example.spoti5.ecobussing.Profiles.IUser;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;

import java.util.List;

/**
 * Created by matildahorppu on 30/09/15.
 */
public class Database implements IDatabase {

    //Database setup
    public static final String FIREBASE = "https://boiling-heat-4034.firebaseio.com/";
    private Firebase firebaseRef;

    public Database() {
        //Initializing firebase ref
        firebaseRef = new Firebase(FIREBASE);
    }

    @Override
    public List<IUser> getUsers() {
        return null;
    }

    @Override
    public List<IUser> getToplist() {
        return null;
    }

    @Override
    public void addUser(String email, String password, final IUser user) throws Exception{

        firebaseRef.createUser(email, password, new Firebase.ResultHandler() {

            @Override
            public void onSuccess() {
                Firebase tmpRef = firebaseRef.child("users").child(user.getUsername());
                tmpRef.setValue(user);

                System.out.println("Successsssssss");
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                throw firebaseError.toException();
            }
        });
    }


}
