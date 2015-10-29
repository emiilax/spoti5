package com.example.spoti5.ecobussing.controller.database.interfaces;

import com.example.spoti5.ecobussing.model.profile.Company;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.model.profile.User;

import java.util.List;

/**
 * Created by erikk on 2015-09-23.
 */
public interface IDatabase {
    List<IUser> getUsers();
    List<IUser> getUserToplistAll();
    List<IUser> getUserToplistMonth();
    List<IUser> getUserToplistYear();

    List<IProfile> getCompanies();
    List<IProfile> getCompaniesToplistAll();
    List<IProfile> getCompaniesToplistMonth();
    List<IProfile> getCompaniesToplistYear();

    void addUser(String email, String password, final User user, IDatabaseConnected connection);
    void addCompany(String name, final Company company, IDatabaseConnected connection);
    void loginUser(String email, String password, IDatabaseConnected connection);
    int getErrorCode();
    IUser getUser(String email);
    void changePassword(String email, String oldPassword, String newPassword,  IDatabaseConnected connection);

    int getPosition(IUser user);
    int getPosition(Company comp);

    IProfile getCompany(String name);

    void updateUser(IUser user);
    void updateCompany(Company company);

    void removeCompany(Company company);

}




