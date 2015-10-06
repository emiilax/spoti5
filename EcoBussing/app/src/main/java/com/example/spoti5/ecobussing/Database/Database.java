package com.example.spoti5.ecobussing.Database;

import android.provider.ContactsContract;
import android.widget.ArrayAdapter;

import com.example.spoti5.ecobussing.JsonClasses.Directions.Directions;
import com.example.spoti5.ecobussing.Profiles.BusinessProfile;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by matildahorppu on 30/09/15.
 */
public class Database implements IDatabase{

    private int errorCode;
    //Database setup
    public static final String FIREBASE = "https://boiling-heat-4034.firebaseio.com/users/";
    private Firebase firebaseRef;
    private boolean successLogin = false;
    private List<IUser> allUsers;
    private List<IProfile> allCompanies;
    private String UID;

    public Database() {
        //Initializing firebase ref
        firebaseRef = new Firebase(FIREBASE);
        allUsers = generateUserList();
    }

    @Override
    public List<IUser> getUserToplist() {

        List<IUser> topList = getUsers();

        Collections.sort(topList, new Comparator<IUser>() {
            // @Override
            public int compare(IUser lhs, IUser rhs) {
                if(lhs.getCO2Saved() < rhs.getCO2Saved()){
                    return -1;
                }else if(lhs.getCO2Saved() > rhs.getCO2Saved()){
                    return 1;
                }else{
                    return 0;
                }
            }
        });

        return topList;
    }

    @Override
    public List<IUser> getCompTopList() {

        List<IProfile> topList = getCompanies();

        return null;
    }

    public int getErrorCode(){
        return errorCode;
    }

    @Override
    public IUser getUser(String email) {
        for(IUser u: getUsers()){
            if(u.getEmail().equals(email)){
                return u;
            }
        }
        return null;
    }

    @Override
    public void updateUser(IUser user) {
        Firebase ref = firebaseRef.child(editEmail(user.getEmail()));
        ref.setValue(user);
    }

    @Override
    public List getCompanyMembers(String companyKey) {
        return null;
    }

    @Override
    public void addUser(String email, String password, final User theUser, final IDatabaseConnected connection){
        errorCode = ErrorCodes.NO_ERROR;
        firebaseRef.child("users").createUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Firebase tmpRef = firebaseRef.child(editEmail(theUser.getEmail()));
                tmpRef.setValue(theUser, new Firebase.CompletionListener() {
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
                if (tmpError == FirebaseError.EMAIL_TAKEN) {
                    errorCode = ErrorCodes.BAD_EMAIL;
                } else if (tmpError == FirebaseError.DISCONNECTED || tmpError == FirebaseError.NETWORK_ERROR) {
                    errorCode = ErrorCodes.NO_CONNECTION;
                } else {
                    errorCode = ErrorCodes.UNKNOWN_ERROR;
                }
                connection.addingUserFinished();
            }
        });


    }

    @Override
    public void addCompany(final String name, String password, final BusinessProfile company, final IDatabaseConnected connection) {

        //key kan kanske vara lösenordet för att ansluta till företaget?
        errorCode = ErrorCodes.NO_ERROR;
        firebaseRef.child("companies").createUser(name, password, new Firebase.ResultHandler(){

            @Override
            public void onSuccess() {
                Firebase tmpRef = firebaseRef.child(name);
                tmpRef.setValue(company, new Firebase.CompletionListener() {
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
                connection.addingUserFinished();
            }
        });


    }

    private String editEmail(String email){
        email = email.toLowerCase();
        email = email.replace('.',',');
        return email;
    }


    @Override
    public void loginUser(String email, String password, final IDatabaseConnected connection){
        errorCode = ErrorCodes.NO_ERROR;
        firebaseRef.child("users").authWithPassword(email, password, new Firebase.AuthResultHandler() {

            @Override
            public void onAuthenticated(AuthData authData) {
                errorCode = ErrorCodes.NO_ERROR;
                connection.loginFinished();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
                int tmpError = firebaseError.getCode();
                if (tmpError == FirebaseError.INVALID_CREDENTIALS) {
                    errorCode = ErrorCodes.WRONG_CREDENTIALS;
                } else if (tmpError == FirebaseError.DISCONNECTED || tmpError == FirebaseError.NETWORK_ERROR) {
                    errorCode = ErrorCodes.NO_CONNECTION;
                } else if (tmpError == FirebaseError.INVALID_EMAIL) {
                    errorCode = ErrorCodes.BAD_EMAIL;
                } else {
                    errorCode = ErrorCodes.UNKNOWN_ERROR;
                }
                connection.loginFinished();
            }
        });
    }

    private List<IUser> generateUserList(){
        final ArrayList userList = new ArrayList();

        firebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    userList.clear();
                    for (DataSnapshot userSnapshots : dataSnapshot.getChildren()) {
                        IUser user = new User((String) userSnapshots.child("email").getValue());
                        user.setAge(((Long) userSnapshots.child("age").getValue()).intValue());
                        user.setCarPetrolConsumption((Double) userSnapshots.child("carPetrolConsumption").getValue());
                        user.setPosition(((Long) userSnapshots.child("position").getValue()).intValue());
                        user.setName((String) userSnapshots.child("name").getValue());
                        user.setDistance((double) userSnapshots.child("distance").getValue());
                        user.setCO2Saved((double) userSnapshots.child("co2Saved").getValue());
                        user.setCurrentDistance((double) userSnapshots.child("currentDistance").getValue());
                        user.setMoneySaved((double) userSnapshots.child("moneySaved").getValue());

                        userList.add(user);

                    }
                } catch (FirebaseException var4) {
                    System.out.println(var4.getMessage());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed " + firebaseError.getMessage());

            }
        });

        return userList;
    }

    private List<IProfile> generateCompanyList(){
        return null;
    }

    @Override
    public List<IUser> getUsers() {
        if(allUsers != null){
            return allUsers;
        } else {
            return generateUserList();
        }
    }
    @Override
    public List<IProfile> getCompanies(){
        if(allCompanies != null){
            return allCompanies;
        }else{
            return generateCompanyList();
        }
    }

}
