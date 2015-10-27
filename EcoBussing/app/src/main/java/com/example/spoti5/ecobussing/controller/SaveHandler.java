package com.example.spoti5.ecobussing.controller;
import android.content.Context;

import com.example.spoti5.ecobussing.Activites.ActivityController;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;

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
    private static IUser currentUser;
    private static Context context = ActivityController.getContext();

    /**
     * Loads user from local harddrive. If not user exists this will return null
     * @return Null if no user is logged locally
     */
    public static IUser getCurrentUser() {
        if(currentUser == null){
            try {
                FileInputStream fileInputStream = context.openFileInput(filename);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                currentUser = (IUser) objectInputStream.readObject();
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
        return currentUser;
    }

    public static void changeUser(IUser newUser) {
        Context context = ActivityController.getContext();
        currentUser = newUser;
        database.updateUser(newUser);
        SaveUser();
    }

    public static void SaveUser() {
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(currentUser);
            oos.close();
            fos.close();

        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}