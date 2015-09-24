package com.example.spoti5.ecobussing.Database;

import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erikk on 2015-09-23.
 */
public class TmpDatabase implements IDatabase{

    List<IUser> allUsers;
    List<IUser> topListUsers;

    public TmpDatabase(){
        allUsers = new ArrayList<>();
        topListUsers = new ArrayList<>();
    }

    @Override
    public List<IUser> getUsers() {
        return allUsers;
    }

    @Override
    public List<IUser> getToplist() {
        return topListUsers;
    }

    @Override
    public boolean usernameExists(String username) {
        for(IUser u: allUsers) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addUser(IUser user) throws UsernameAlreadyExistsException {
        if(!usernameExists(user.getUsername())){
            allUsers.add(user);
        } else {
            throw new UsernameAlreadyExistsException();
        }
    }
}
