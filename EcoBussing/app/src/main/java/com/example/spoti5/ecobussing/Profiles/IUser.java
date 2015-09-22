package com.example.spoti5.ecobussing.Profiles;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IUser extends IProfile {

    public String getEmail();

    public void setPassword(String password);
    public boolean checkPassword(String password);
    public boolean checkUsername(String username);

    public void incMoneySaved(double moneySaved);
}
