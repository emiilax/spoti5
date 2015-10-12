package com.example.spoti5.ecobussing.Database;

import com.example.spoti5.ecobussing.Profiles.BusinessProfile;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matildahorppu on 30/09/15.
 */
public class Database implements IDatabase{

    private int errorCode;

    //Database setup
    public static final String FIREBASE = "https://boiling-heat-4034.firebaseio.com/";
    private Firebase firebaseRef;
    private List<IProfile> allCompanies;

    private List<IUser> allUsers = new ArrayList<>();
    private List<IUser> topListAll = new ArrayList<>();
    private List<IUser> topListMonth = new ArrayList<>();
    private List<IUser> topListYear = new ArrayList<>();



    private boolean allGenerated;


    public Database() {
        //Initializing firebase ref
        firebaseRef = new Firebase(FIREBASE);
        generateUserList();
        generateToplistAll();
        generateToplistMonth();
        generateToplistYear();
    }

    private void generateToplistAll() {
        final Query queryRef = firebaseRef.child("users").orderByChild("co2Tot");

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                topListAll.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User u = snapshot.getValue(User.class);
                    topListAll.add(u);
                    allGenerated = true;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                allGenerated = false;
            }
        });
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
        if(user != null) {
            Firebase ref = firebaseRef.child("users").child(editEmail(user.getEmail()));
            ref.setValue(user);
        }
    }

    @Override
    public void updateCompany(IProfile company) {
        Firebase ref = firebaseRef.child("companies").child(company.getName());
        ref.setValue(company);
    }


    @Override
    public List getCompanyMembers(String companyKey) {
        return null;
    }

    /**
     * Adds user to database. This takes time and onSuccess is called if the connection and
     * adding user went ok. Otherwise onError is called and stores the errorcode that can be
     * fetched with getErrorCode()
     * @param email the email that the user will have as credential
     * @param password the password the user will have as credential
     * @param theUser all variables that will be stored in the database
     * @param connection  the origin class that is called after the user is added or failed to be added
     */
    @Override
    public void addUser(String email, String password, final User theUser, final IDatabaseConnected connection){
        errorCode = ErrorCodes.NO_ERROR;
        firebaseRef.child("users").createUser(email, password, new Firebase.ResultHandler() {

            @Override
            public void onSuccess() {
                Firebase tmpRef = firebaseRef.child("users").child(editEmail(theUser.getEmail()));
                tmpRef.setValue(theUser, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        errorCode = ErrorCodes.NO_ERROR;
                        connection.addingFinished();
                    }
                });
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
                setErrorCode(firebaseError);
                connection.addingFinished();
            }
        });
    }

    private void setErrorCode(FirebaseError error){
        int tmpError = error.getCode();
        if (tmpError == FirebaseError.INVALID_CREDENTIALS) {
            errorCode = ErrorCodes.WRONG_CREDENTIALS;
        } else if (tmpError == FirebaseError.DISCONNECTED || tmpError == FirebaseError.NETWORK_ERROR) {
            errorCode = ErrorCodes.NO_CONNECTION;
        } else if (tmpError == FirebaseError.INVALID_EMAIL || tmpError == FirebaseError.EMAIL_TAKEN) {
            errorCode = ErrorCodes.BAD_EMAIL;
        } else {
            errorCode = ErrorCodes.UNKNOWN_ERROR;
        }
    }

    @Override
    public void addCompany(final String name, final BusinessProfile company, final IDatabaseConnected connection) {

        errorCode = ErrorCodes.NO_ERROR;
        final Firebase tmpRef = firebaseRef.child("companies");
        tmpRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(name)) {
                    tmpRef.child(name).setValue(company);
                    errorCode = ErrorCodes.NO_ERROR;
                } else {
                    errorCode = ErrorCodes.COMPANY_ALREADY_EXISTS;
                }
                connection.addingFinished();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
                setErrorCode(firebaseError);
            }
        });
    }

    /**
     *
     * @param email that will be edited
     * @return a new email that firebase can use as node name ('.' are not allowed)
     */
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
                System.out.println("Database logged in");
                errorCode = ErrorCodes.NO_ERROR;
                connection.loginFinished();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
                setErrorCode(firebaseError);
                connection.loginFinished();
            }
        });
    }

    private void generateUserList(){

        firebaseRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    allUsers.clear();
                    for (DataSnapshot userSnapshots : dataSnapshot.getChildren()) {
                        User user = userSnapshots.getValue(User.class);
                        allUsers.add(user);
                        System.out.println(user.getEmail());

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
    }

    private List<IProfile> generateCompanyList(){
        return null;
    }

    @Override
    public List<IUser> getUsers() {
        if(allUsers.size() != 0){
            return allUsers;
        } else {
            generateUserList();
            return allUsers;
        }
    }

    @Override
    public List<IUser> getUserToplistAll() {
        return topListAll;
    }

    @Override
    public List<IProfile> getCompanies(){
        if(allCompanies != null){
            return allCompanies;
        }else{
            return generateCompanyList();
        }
    }

    private void generateToplistMonth() {
        final Query queryRef = firebaseRef.child("users").orderByChild("co2CurrentMonth");

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                topListMonth.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User u = snapshot.getValue(User.class);
                    topListMonth.add(u);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                allGenerated = false;
            }
        });
    }

    @Override
    public List<IUser> getUserToplistMonth() {
        return getUserToplistMonth();
    }

    private void generateToplistYear() {
        final Query queryRef = firebaseRef.child("users").orderByChild("co2CurrentYear");

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                topListYear.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User u = snapshot.getValue(User.class);
                    topListYear.add(u);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                allGenerated = false;
            }
        });
    }

    @Override
    public List<IUser> getUserToplistYear() {
        return topListYear;
    }

    public boolean isAllGenerated() {
        return allGenerated;
    }

}
