package com.example.spoti5.ecobussing.Profiles;

/**
 * Created by erikk on 2015-09-16.
 */
public interface IUser extends IProfile {

    public String getEmail();

    public void setPassword(String password);
    public boolean checkPassword(String password);
    public boolean checkUsername(String username);
    public int getAge();
    public int getPosition();

    public void setAge(int age);
    public void setPosition(int position);
    public void incMoneySaved(double moneySaved);
}
