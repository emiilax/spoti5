package com.example.spoti5.ecobussing.controller;
import android.content.Context;

import com.example.spoti5.ecobussing.controller.viewcontroller.activities.ActivityController;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.model.profile.DatabaseUser;
import com.example.spoti5.ecobussing.controller.profile.User;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IUser;


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
        if(du != null){
            IUser user = new User(du);
            return user;
        }
        return null;
    }

    public static void changeUser(IUser newUser) {
        if(newUser != null) {
            Context context = ActivityController.getContext();
            du = newUser.getDatabaseUser();
            database.updateUser(newUser);
        }
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
