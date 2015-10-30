
package com.example.spoti5.ecobussing.io.net;

import com.example.spoti5.ecobussing.model.profile.DatabaseCompany;
import com.example.spoti5.ecobussing.model.profile.DatabaseUser;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabaseConnected;
import com.example.spoti5.ecobussing.controller.profile.Company;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.controller.profile.User;
import com.example.spoti5.ecobussing.model.ErrorCodes;
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
public class Database implements IDatabase {

    private int errorCode;
    private String userString = "users";
    private String companiesString = "companies";

    //Database setup
    private static final String FIREBASE = "https://ecotravel.firebaseio.com/";
    private Firebase firebaseRef;

    private List<IUser> allUsers = new ArrayList<>();
    private List<IUser> topListAll = new ArrayList<>();
    private List<IUser> topListMonth = new ArrayList<>();
    private List<IUser> topListYear = new ArrayList<>();

    private List<IProfile> allCompanies = new ArrayList<>();
    private List<IProfile> topListAllCompanies = new ArrayList<>();
    private List<IProfile> topListMonthCompanies = new ArrayList<>();
    private List<IProfile> topListYearCompanies = new ArrayList<>();

    private static final int allValue = 100, topListAllValue = 101, topListMonthValue = 102, topListYearValue = 103;


    private boolean allGenerated = true;


    public Database() {
        //Initializing firebase ref
        firebaseRef = new Firebase(FIREBASE);
        generateAll();
    }

    /*
        Retrives all toplists of users and companies. Updates all so they all have same values
     */
    private void generateAll(){
        generateUserList(allValue);
        generateUserList(topListAllValue, "co2Tot");
        generateUserList(topListMonthValue, "co2CurrentMonth");
        generateUserList(topListYearValue, "co2CurrentYear");

        generateCompaniesList(allValue);
        generateCompaniesList(topListAllValue, "pointTot");
        generateCompaniesList(topListMonthValue, "pointCurrentMonth");
        generateCompaniesList(topListYearValue, "pointCurrentYear");

    }

    /**
     * For retriving potential errors that occurs. Use ErrorCodes class to check
     * what the error is
     * @return the errorcode value as integer
     */
    public int getErrorCode(){
        return errorCode;
    }

    /**
     * Use to retrive a specific user from the database
     * @param email the email of the user you want to retrive
     * @return an IUser with the corresponding email
     */
    @Override
    public IUser getUser(String email) {
        for(IUser u: getUsers()){
            if(u.getEmail().equals(email)){
                return u;
            }
        }
        return null;
    }

    /**
     * Retrives the position of a user in the toplist all
     * @param user The user you want the position of.
     * @return The position value as an integer
     */
    @Override
    public int getPosition(IUser user) {
        int index = topListAll.size();
        for(IUser u: topListAll){
            if(u.getEmail().equals(user.getEmail())){
                return index;
            }
            index = index -1;
        }
        return index;
    }

    /**
     * Retrives the position of a company in the toplist all
     * @param comp The company you want the position of.
     * @return The position value as an integer
     */
    @Override
    public int getPosition(Company comp){
        int index = 0;
        for(IProfile u: topListAllCompanies){
            if(u.getName().equals(u.getName())){
                return index;
            }
            index = index -1;
        }
        return index;
    }

    /**
     * Used to retrive a specific company in the database
     * @param name The name of the company you want to retrive
     * @return The company with the specific name
     */
    @Override
    public IProfile getCompany(String name){

        for(IProfile c: getCompanies()){
            if(c.getName().equals(name)){
                System.out.println(c.getName());
                return c;
            }
        }
        return null;
    }

    /**
     * Updates an already exsiiting user on the database with new values
     * @param user The user that will be updated
     */
    @Override
    public void updateUser(IUser user) {
        if(user != null) {
            Firebase ref = firebaseRef.child(userString).child(editEmail(user.getEmail()));
            ref.setValue(user.getDatabaseUser());
            generateAll();
        }
    }

    /**
     * Updates an already exsiting company on the database with new values
     * @param company The company that will be updated
     */
    @Override
    public void updateCompany(Company company) {
        if (company != null) {
            Firebase ref = firebaseRef.child(companiesString).child(company.getName());
            ref.setValue(company.getDatabaseCompany());
            generateAll();
        }
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
        firebaseRef.child(userString).createUser(email, password, new Firebase.ResultHandler() {

            @Override
            public void onSuccess() {
                Firebase tmpRef = firebaseRef.child(userString).child(editEmail(theUser.getEmail()));
                DatabaseUser du = theUser.getDatabaseUser();
                tmpRef.setValue(du, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        errorCode = ErrorCodes.NO_ERROR;
                        connection.addingFinished();
                        generateAll();
                    }
                });
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                System.out.println("failed addUser()");
                System.out.println(firebaseError.getMessage());
                setErrorCode(firebaseError);
                connection.addingFinished();
            }
        });
    }

    /**
     * Changes the password on an existing user on the database
     * @param email Email of the user you want to change the password on
     * @param oldPassword The users old password
     * @param newPassword The users new password
     * @param connection the origin class that is called after the database changed password or failed to changed password
     */
    @Override
    public void changePassword(String email, String oldPassword, String newPassword, final IDatabaseConnected connection) {
        errorCode = ErrorCodes.NO_ERROR;
        firebaseRef.child(userString).changePassword(email, oldPassword, newPassword, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                errorCode = ErrorCodes.NO_ERROR;
                connection.loginFinished();
                generateAll();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                setErrorCode(firebaseError);
                System.out.println(firebaseError.getMessage());
                connection.loginFinished();
            }
        });
    }


    /*
    Sets errorcode variable
     */
    private void setErrorCode(FirebaseError error) {
        int tmpError = error.getCode();
        if (tmpError == FirebaseError.INVALID_CREDENTIALS || tmpError == FirebaseError.INVALID_PASSWORD) {
            errorCode = ErrorCodes.WRONG_CREDENTIALS;
        } else if (tmpError == FirebaseError.DISCONNECTED || tmpError == FirebaseError.NETWORK_ERROR) {
            errorCode = ErrorCodes.NO_CONNECTION;
        } else if (tmpError == FirebaseError.INVALID_EMAIL) {
            errorCode = ErrorCodes.BAD_EMAIL;
        } else if (tmpError == FirebaseError.EMAIL_TAKEN){
            errorCode = ErrorCodes.EMAIL_ALREADY_EXISTS;
        } else {
            errorCode = ErrorCodes.UNKNOWN_ERROR;
        }
    }

    /**
     * Adds a new company to the database
     * @param name The name of the new company
     * @param company The company class containing the values of the new company
     * @param connection The class that is called after the addition has failed or succeeded
     */
    @Override
    public void addCompany(final String name, final Company company, final IDatabaseConnected connection) {

        errorCode = ErrorCodes.NO_ERROR;
        final Firebase tmpRef = firebaseRef.child(companiesString);
        tmpRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(name)) {
                    tmpRef.child(name).setValue(company.getDatabaseCompany());
                    errorCode = ErrorCodes.NO_ERROR;
                    generateAll();
                } else {
                    errorCode = ErrorCodes.COMPANY_ALREADY_EXISTS;
                }
                connection.addingFinished();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("failed addCompany()");
                System.out.println(firebaseError.getMessage());
                setErrorCode(firebaseError);
            }
        });
    }

    /**
     * Removes a company from the database
     * @param company The company that will be removed from the database
     */
    @Override
    public void removeCompany(Company company) {
        final Firebase tmpRef = firebaseRef.child(companiesString);
        tmpRef.child(company.getName()).removeValue();
        generateAll();
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


    /**
     * Checks if credentials are valid and sets the error code accordingly
     * @param email The email of the user that will be logged in
     * @param password The password of the user that will be logged in
     * @param connection The class that will be called after the log in i successfull or not
     */
    @Override
    public void loginUser(String email, String password, final IDatabaseConnected connection){
        errorCode = ErrorCodes.NO_ERROR;
        firebaseRef.child(userString).authWithPassword(email, password, new Firebase.AuthResultHandler() {

            @Override
            public void onAuthenticated(AuthData authData) {
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

    /*
    Generates a user list, not in any specific order
     */
    private void generateUserList(final int listValue){

        firebaseRef.child(userString).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    clearListUser(listValue);
                    for (DataSnapshot userSnapshots : dataSnapshot.getChildren()) {
                        DatabaseUser du = userSnapshots.getValue(DatabaseUser.class);
                        IUser user = new User(du);
                        addUserToList(listValue, user);
                    }
                } catch (FirebaseException var4) {
                    allGenerated = false;
                    System.out.println(var4.getMessage());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed " + firebaseError.getMessage());
                allGenerated = false;
            }
        });
    }

    /*
    Generates a user list with a specific sorting node
     */
    private void generateUserList(final int listValue, String sorter) {
        final Query queryRef = firebaseRef.child(userString).orderByChild(sorter);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clearListUser(listValue);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DatabaseUser du = snapshot.getValue(DatabaseUser.class);
                    IUser user = new User(du);
                    addUserToList(listValue, user);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                allGenerated = false;
            }
        });
    }

    private void clearListUser(int listValue){
            switch(listValue) {
                case Database.allValue:
                    allUsers.clear();
                    break;
                case Database.topListAllValue:
                    topListAll.clear();
                    break;
                case Database.topListMonthValue:
                    topListMonth.clear();
                    break;
                case Database.topListYearValue:
                    topListYear.clear();
                    break;
            }
        }

    /*
    Generates company list, unsorted
     */
    private void generateCompaniesList(final int listValue){

        firebaseRef.child(companiesString).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    clearListCompany(listValue);
                    for (DataSnapshot companySnapshots : dataSnapshot.getChildren()) {
                        DatabaseCompany du = companySnapshots.getValue(DatabaseCompany.class);
                        IProfile company = new Company(du);
                        addCompanyToList(listValue, company);

                    }
                } catch (FirebaseException var4) {
                    allGenerated = false;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed " + firebaseError.getMessage());

            }
        });

    }

    /*
    Generates company list with specific sorting node
     */
    private void generateCompaniesList(final int listValue, String sorter) {
        final Query queryRef = firebaseRef.child(companiesString).orderByChild(sorter);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               try{
                    clearListCompany(listValue);
                    for (DataSnapshot companySnapshots : dataSnapshot.getChildren()) {
                        DatabaseCompany du = companySnapshots.getValue(DatabaseCompany.class);
                        IProfile company = new Company(du);
                        addCompanyToList(listValue, company);
                    }
                } catch(FirebaseException e){
                    allGenerated = false;
                    System.out.println(listValue);
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                allGenerated = false;
            }
        });
    }

    /*
    Add a user to a list
     */
    private void addUserToList(int listValue, IUser user){
            switch(listValue){
                case Database.allValue: allUsers.add(user);
                    break;
                case Database.topListAllValue: topListAll.add(user);
                    break;
                case Database.topListMonthValue: topListMonth.add(user);
                    break;
                case Database.topListYearValue: topListYear.add(user);
                    break;
            }
        }

    private void clearListCompany(int listValue){
        switch (listValue){
            case Database.allValue: allCompanies.clear();
                break;
            case Database.topListAllValue: topListAllCompanies.clear();
                break;
            case Database.topListMonthValue: topListMonthCompanies.clear();
                break;
            case Database.topListYearValue: topListYearCompanies.clear();
                break;
        }
    }

    private void addCompanyToList(int listValue, IProfile company){
        switch (listValue) {
            case Database.allValue:
                allCompanies.add(company);
                break;
            case Database.topListAllValue:
                topListAllCompanies.add(company);
                break;
            case Database.topListMonthValue:
                topListMonthCompanies.add(company);
                break;
            case Database.topListYearValue:
                topListYearCompanies.add(company);
                break;
        }
    }

    private List<IProfile> generateCompanyList(){
        return null;
    }

    /**
     * @return A list of unsorted users
     */
    @Override
    public List<IUser> getUsers() {
        return allUsers;
    }

    /**
     * @return A list of user sorted by co2saved, lowest first
     */
    @Override
    public List<IUser> getUserToplistAll() {
        return topListAll;
    }

    /**
     * @return A list of companies, unsorted
     */
    @Override
    public List<IProfile> getCompanies(){
        return allCompanies;
    }

    /**
     *@return A list of companies sorted by point total, lowest first
     */
    @Override
    public List<IProfile> getCompaniesToplistAll() {
        return topListAllCompanies;
    }

    /**
     * @return A list of companies sorted by monthly point total, lowest first
     */
    @Override
    public List<IProfile> getCompaniesToplistMonth() {
        return topListMonthCompanies;
    }

    /**
     * @return A list of companies sorted by yearly point total, lowest first
     */
    @Override
    public List<IProfile> getCompaniesToplistYear() {
        return topListYearCompanies;
    }

    /**
     * @return A list of users sorted by monthly co2 saved, lowest first
     */
    @Override
    public List<IUser> getUserToplistMonth() {
        return topListMonth;
    }

    /**
     * @return A list of users sorted by yearly co2 saved, lowest first
     */
    @Override
    public List<IUser> getUserToplistYear() {
        return topListYear;
    }

    /**
     * @return A boolean that is true or false depending on if all lists are generated properly
     */
    public boolean isAllGenerated() {
        return allGenerated;
    }

}
