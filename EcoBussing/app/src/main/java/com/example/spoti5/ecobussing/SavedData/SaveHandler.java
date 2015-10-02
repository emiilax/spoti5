package com.example.spoti5.ecobussing.SavedData;
import com.example.spoti5.ecobussing.Profiles.User;

/**
 * Created by hilden on 2015-09-23.
 */
public class SaveHandler {

    private static User currentUser;

    public static User getCurrentUser() {
        if (currentUser == null) {
            currentUser = new User("mail@mail.com", "Sven");
        }
        return currentUser;
    }

    public static void changeUser(User newUser) { currentUser = newUser; }

    public void SaveUser() {}

    public void LoadUser() {}
}
