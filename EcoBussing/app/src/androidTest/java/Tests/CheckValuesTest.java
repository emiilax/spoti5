package Tests;

import android.content.SyncStatusObserver;

import java.util.ArrayList;
import java.util.List;

import Profiles.CheckValues;

/**
 * Created by erikk on 2015-09-16.
 */
public class CheckValuesTest {
    List<String> passwords = new ArrayList<String>();
    List<String> emails = new ArrayList<String>();

    CheckValuesTest(){
        addPasswords();
        checkPasswords();
    }

    private void addPasswords(){
        passwords.add("hej");
        passwords.add("Hej1");
        passwords.add("EttTvåTre");
        passwords.add("1hejsan");
        passwords.add("hejsan1");
        passwords.add("Hejsan1"); // 7
        passwords.add("2Hejsan");
    }

    private void checkPasswords(){
        for(String s: passwords){
            int value = CheckValues.checkPassword(s);
            System.out.println(value);
            if(value == -1) {
                System.out.println(s + " is a correct password");
            } else {
                if (value == 0) {
                    System.out.println(s + " is to short!");
                } else if (value == 1) {
                    System.out.println(s + " doesn't contain an upper case letter");
                } else if (value == 2) {
                    System.out.println(s + " doesn't contain a lower case letter");
                } else if (value == 3) {
                    System.out.println(s + " doesn't contain a number");
                } else {
                    System.out.println(s + "is weird");
                }
            }
        }
    }

    public static void main(String[] args){
        new CheckValuesTest();
    }
}
