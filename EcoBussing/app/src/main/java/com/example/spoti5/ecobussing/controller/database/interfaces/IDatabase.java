package com.example.spoti5.ecobussing.controller.database.interfaces;

import com.example.spoti5.ecobussing.controller.profile.Company;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.controller.profile.User;

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

    public void addUser(String email, String password, final User user, IDatabaseConnected connection);
    public void addCompany(String name, final Company company, IDatabaseConnected connection);
    public void loginUser(String email, String password, IDatabaseConnected connection);
    public int getErrorCode();
    public IUser getUser(String email);
    public void changePassword(String email, String oldPassword, String newPassword,  IDatabaseConnected connection);

    public int getPosition(IUser user);
    public int getPosition(Company comp);

    public IProfile getCompany(String name);

    public void updateUser(IUser user);
    public void updateCompany(Company company);

    public void removeCompany(Company company);

}




