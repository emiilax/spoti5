package com.example.spoti5.ecobussing.Database;

import com.example.spoti5.ecobussing.Profiles.IUser;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.firebase.client.ValueEventListener;

import java.sql.SQLOutput;
import java.util.List;

/**
 * Created by matildahorppu on 30/09/15.
 */
public class Database implements IDatabase {

    //Database setup
    public static final String FIREBASE = "https://boiling-heat-4034.firebaseio.com/";
    private Firebase firebaseRef;
    private boolean correctUsername = false;
    private boolean correctEmail = false;
    private boolean isDone = false;

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

    public boolean checkIfCorrectUsername() {return correctUsername; }

    public boolean isDone(){
        return isDone;
    }

    @Override
    public void addUser(String email, String password, final IUser user){
        isDone = false;
        correctEmail = false;
        correctUsername = false;

        firebaseRef.createUser(email, password, new Firebase.ResultHandler() {

            @Override
            public void onSuccess() {

                final Firebase tmpRef = firebaseRef.child("users").child(user.getUsername());

                tmpRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() == null) {
                            System.out.println("hello");
                            correctUsername = true;
                            correctEmail = true;
                            isDone = true;
                            tmpRef.setValue(user);
                        } else {
                            correctUsername = false;
                            isDone = true;
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        
                    }
                });

                System.out.println("e" + correctEmail);
                System.out.println("u" + correctUsername);

            }
            @Override
            public void onError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
                correctEmail = false;
                isDone = true;
            }
        });
    }


}
