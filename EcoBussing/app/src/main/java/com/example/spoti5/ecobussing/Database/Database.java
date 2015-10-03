package com.example.spoti5.ecobussing.Database;

import com.example.spoti5.ecobussing.Profiles.IUser;
import com.firebase.client.AuthData;
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

    private int errorCode;
    //Database setup
    public static final String FIREBASE = "https://boiling-heat-4034.firebaseio.com/";
    private Firebase firebaseRef;
    private boolean successLogin = false;

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

    public int getErrorCode(){
        return errorCode;
    }

    @Override
    public void addUser(String email, String password, final IUser user, final IDatabaseConnected connection){
        errorCode = ErrorCodes.NO_ERROR;
        firebaseRef.child("users").createUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Firebase tmpRef = firebaseRef.child("users").push();
                tmpRef.setValue(user, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        errorCode = ErrorCodes.NO_ERROR;
                        connection.addingUserFinished();
                    }
                });
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
                int tmpError = firebaseError.getCode();
                if(tmpError == FirebaseError.EMAIL_TAKEN){
                    errorCode = ErrorCodes.BAD_EMAIL;
                } else if (tmpError == FirebaseError.DISCONNECTED || tmpError == FirebaseError.NETWORK_ERROR){
                    errorCode = ErrorCodes.NO_CONNECTION;
                } else {
                    errorCode = ErrorCodes.UNKNOWN_ERROR;
                }
                connection.addingUserFinished();
            }
        });
    }


    @Override
    public void loginUser(String email, String password, final IDatabaseConnected connection){
        errorCode = ErrorCodes.NO_ERROR;
        firebaseRef.child("users").authWithPassword(email, password, new Firebase.AuthResultHandler(){

            @Override
            public void onAuthenticated(AuthData authData) {
                errorCode = ErrorCodes.NO_ERROR;
                connection.loginFinished();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
                int tmpError = firebaseError.getCode();
                if(tmpError == FirebaseError.INVALID_CREDENTIALS){
                    errorCode = ErrorCodes.WRONG_CREDENTIALS;
                } else if (tmpError == FirebaseError.DISCONNECTED || tmpError == FirebaseError.NETWORK_ERROR) {
                    errorCode = ErrorCodes.NO_CONNECTION;
                } else if(tmpError == FirebaseError.INVALID_EMAIL){
                    errorCode = ErrorCodes.BAD_EMAIL;
                } else {
                    errorCode = ErrorCodes.UNKNOWN_ERROR;
                }
                connection.loginFinished();
            }
        });
    }
}
