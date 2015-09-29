package com.example.spoti5.ecobussing.Database;

import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;

import java.util.List;

/**
 * Created by erikk on 2015-09-23.
 */
public interface IDatabase {
    public List<IUser> getUsers();
    public List<IUser> getToplist();

    public boolean usernameExists(String username);

    /**
     * Throws exception if username already existz
     */
    public void addUser(IUser user) throws UsernameAlreadyExistsException;
}
