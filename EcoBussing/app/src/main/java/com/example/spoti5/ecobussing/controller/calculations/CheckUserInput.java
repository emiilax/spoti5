package com.example.spoti5.ecobussing.controller.calculations;

/**
 * Created by erikk on 2015-09-16.
 */
public class CheckUserInput {


    /**
     * Checks if a password is correct
     * @param password password that will be checked
     * @return -1 if correct, 0 if password is to short,
     * 1 if password doesn't contain upper case letter,
     * 2 if password doesn't contain lower case letter
     * 3 if password doesn't contain a number
     */
    public static int checkPassword(String password){
        if(password.length() < 7){
            return 0;
        } else {
            boolean upper = false;
            boolean lower = false;
            boolean number = false;
            for (char c: password.toCharArray()){
                if(Character.isUpperCase(c)){
                    upper = true;
                }else if(Character.isLowerCase(c)){
                    lower = true;
                } else if (Character.isDigit(c)){
                    number = true;
                }
            }
            if(!upper){
                return 1;
            }else if (!lower){
                return 2;
            }/* else if (!number){
                return 3;
            }*/
        }
        return -1;
    }

    public static boolean checkEmail(String email){
        return (email.contains("@")&&email.contains("."));
    }

    public static boolean checkUsernameAvailabilty(String username){return true;}
}
