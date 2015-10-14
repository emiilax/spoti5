package com.example.spoti5.ecobussing.Database;

import com.example.spoti5.ecobussing.Profiles.Company;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;

import java.util.List;

/**
 * Created by erikk on 2015-09-23.
 */
public interface IDatabase {
    public List<IUser> getUsers();
    public List<IUser> getUserToplistAll();
    public List<IUser> getUserToplistMonth();
    public List<IUser> getUserToplistYear();

    public List<IProfile> getCompanies();
    public List<IProfile> getCompaniesToplistAll();
    public List<IProfile> getCompaniesToplistMonth();
    public List<IProfile> getCompaniesToplistYear();

    public List<IUser> getCompTopList();
    public void addUser(String email, String password, final User user, IDatabaseConnected connection);
    public void addCompany(String name, final Company company, IDatabaseConnected connection);
    public void loginUser(String email, String password, IDatabaseConnected connection);
    public int getErrorCode();
    public IUser getUser(String email);

    public IProfile getCompany(String name);

    public void updateUser(IUser user);
    public void updateCompany(IProfile company);

}




