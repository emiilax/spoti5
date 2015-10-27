package com.example.spoti5.ecobussing.controller;
import android.content.Context;

import com.example.spoti5.ecobussing.Activites.ActivityController;
<<<<<<< HEAD:EcoBussing/app/src/main/java/com/example/spoti5/ecobussing/SavedData/SaveHandler.java
import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.model.profile.DatabaseUser;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
=======
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
>>>>>>> 204d82174c30e32b865a621582afd4605b44f640:EcoBussing/app/src/main/java/com/example/spoti5/ecobussing/controller/SaveHandler.java

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by hilden on 2015-09-23.
 */
public class SaveHandler {
    private static final long serialVersionUID = 7863262235394607247L;
    private static String filename = "ecoTravel.ser";
    private static IDatabase database = DatabaseHolder.getDatabase();
    private static DatabaseUser du;
    private static Context context = ActivityController.getContext();

    /**
     * Loads user from local harddrive. If not user exists this will return null
     * @return Null if no user is logged locally
     */
    public static IUser getCurrentUser() {
        if(du == null){
            try {
                FileInputStream fileInputStream = context.openFileInput(filename);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                du = (DatabaseUser)objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch(NullPointerException e){
                e.printStackTrace();
            }

        }
        IUser user = new User(du);
        return user;
    }

    public static void changeUser(IUser newUser) {
        Context context = ActivityController.getContext();
        du = newUser.getDatabaseUser();
        database.updateUser(newUser);
        SaveUser();
    }

    public static void SaveUser() {
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(du);
            oos.close();
            fos.close();

        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
