package com.example.spoti5.ecobussing;

import java.util.ArrayList;
import java.util.List;

import com.example.spoti5.ecobussing.Calculations.CheckCreateUserInput;

/**
 * Created by erikk on 2015-09-16.
 */
public class CheckValuesTest {
    List<String> passwords = new ArrayList<String>();
    List<String> emails = new ArrayList<String>();

    CheckValuesTest(){
        addPasswords();
        checkPasswords();
        addEmails();
        checkEmails();
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

    private void addEmails(){
        emails.add("erik");
        emails.add("erik.");
        emails.add("erik.k@");
        emails.add("erik.karlkvist@gmail.com");
        emails.add("dadsa@dsadsa.dsadsa");
    }

    private void checkPasswords(){
        for(String s: passwords){
            int value = CheckCreateUserInput.checkPassword(s);
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

    private void checkEmails(){
        for(String s: emails){
            if(CheckCreateUserInput.checkEmail(s)){
                System.out.println(s + " is a correct email");
            } else {
                System.out.println(s + " is not a correct email");
            }
        }
    }

    public static void main(String[] args){
        new CheckValuesTest();
    }
}
