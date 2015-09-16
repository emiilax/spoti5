package Profiles;

/**
 * Created by erikk on 2015-09-16.
 */
public class CheckValues {

    public static boolean checkPassword(String password){
        return true;
    }

    public static boolean checkEmail(String email){
        return email.contains("@");
    }

    public static boolean checkUsernameAvailabilty(String username){return true;}
}
