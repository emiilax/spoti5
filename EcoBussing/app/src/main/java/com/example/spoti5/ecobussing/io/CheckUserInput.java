package com.example.spoti5.ecobussing.io;

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
            for (char c: password.toCharArray()){
                if(Character.isUpperCase(c)){
                    upper = true;
                }else if(Character.isLowerCase(c)){
                    lower = true;
                }
            }
            if(!upper){
                return 1;
            }else if (!lower){
                return 2;
            }
        }
        return -1;
    }

    public static boolean checkEmail(String email){
        String patterns = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(patterns);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

}
