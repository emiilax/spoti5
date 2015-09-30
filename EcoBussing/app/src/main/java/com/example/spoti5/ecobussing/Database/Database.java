package com.example.spoti5.ecobussing.Database;

import com.example.spoti5.ecobussing.Profiles.IUser;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.firebase.client.ValueEventListener;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by matildahorppu on 30/09/15.
 */
public class Database implements IDatabase{

    //Database setup
    public static final String FIREBASE = "https://boiling-heat-4034.firebaseio.com/";
    private Firebase firebaseRef;
    private boolean correctUsername = false;
    private boolean correctEmail = false;

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

    public boolean checkIfCorrectEmail(){
        return correctEmail;
    }

    @Override
    public void addUser(String email, String password, final IUser user, final IDatabaseConnected connection){
        correctEmail = false;

        firebaseRef.child("users").createUser(email, password, new Firebase.ResultHandler() {

            @Override
            public void onSuccess() {

                Firebase tmpRef = firebaseRef.child("users").push();
                tmpRef.setValue(user, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        correctEmail = true;
                        connection.addingUserFinished();
                    }
                });


            }

            @Override
            public void onError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
                correctEmail = false;
                connection.addingUserFinished();
            }
        });
    }
}
