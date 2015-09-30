package com.example.spoti5.ecobussing.Database;

import android.app.Activity;

import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.firebase.client.FirebaseException;

import java.util.List;

/**
 * Created by erikk on 2015-09-23.
 */
public interface IDatabase {
    public List<IUser> getUsers();
    public List<IUser> getToplist();
    public void addUser(String email, String password, final IUser user, IDatabaseConnected connection);
    public boolean checkIfCorrectEmail();
}




