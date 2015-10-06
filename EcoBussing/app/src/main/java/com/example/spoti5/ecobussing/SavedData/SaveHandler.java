package com.example.spoti5.ecobussing.SavedData;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;

/**
 * Created by hilden on 2015-09-23.
 */
public class SaveHandler {

    private static IUser currentUser;

    public static IUser getCurrentUser() {
        if (currentUser == null) {
            currentUser = new User("mail@mail.com", "Sven");
        }
        return currentUser;
    }

    public static void changeUser(IUser newUser) { currentUser = newUser; }

    public void SaveUser() {}

    public void LoadUser() {}
}
