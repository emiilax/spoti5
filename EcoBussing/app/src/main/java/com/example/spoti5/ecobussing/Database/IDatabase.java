package com.example.spoti5.ecobussing.Database;

import android.app.Activity;

import com.example.spoti5.ecobussing.Profiles.BusinessProfile;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.firebase.client.FirebaseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erikk on 2015-09-23.
 */
public interface IDatabase {
    public List<IUser> getUsers();
    public List<IUser> getUserToplistAll();

    public List<IUser> getCompTopList();
    public void addUser(String email, String password, final User user, IDatabaseConnected connection);
    public void addCompany(String name, String password, final BusinessProfile company, IDatabaseConnected connection);
    public void loginUser(String email, String password, IDatabaseConnected connection);
    public int getErrorCode();
    public IUser getUser(String email);
    public List<IProfile> getCompanies();
    public void updateUser(IUser user);
}




