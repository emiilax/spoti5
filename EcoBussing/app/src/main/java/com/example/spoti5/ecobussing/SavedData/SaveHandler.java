package com.example.spoti5.ecobussing.SavedData;
import android.content.Context;

import com.example.spoti5.ecobussing.Activites.StartActivites;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.example.spoti5.ecobussing.Activites.StartActivites.*;

/**
 * Created by hilden on 2015-09-23.
 */
public class SaveHandler {
    private static final long serialVersionUID = 7863262235394607247L;
    private static String filename = "ecoTravel.ser";

    private static String filenamePass = "pass.ser";

    private static IUser currentUser;
    private static String password;

    public static IUser getCurrentUser() {
        if(currentUser == null){
            try {
                Context context = StartActivites.getContext();
                FileInputStream fileInputStream = context.openFileInput(filename);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                currentUser = (IUser) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        if(currentUser == null){
            currentUser = new User("tmp@mail.mm", "sven");
        }
        return currentUser;
    }

    public static void changeUser(IUser newUser) {
        Context context = StartActivites.getContext();
        currentUser = newUser;
        SaveUser(context);}

    public static  void setPassword(String newPassword){
        Context context = StartActivites.getContext();
        password = newPassword;
        SavePassword(context);
    }

    private static void SavePassword(Context context) {
        try{
            FileOutputStream passfos = context.openFileOutput(filenamePass, Context.MODE_PRIVATE);
            ObjectOutputStream passOos = new ObjectOutputStream(passfos);
            passOos.writeObject(password);
            passOos.close();
            passfos.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getPassword(){
        Context context = StartActivites.getContext();
        if(password == null){
            try {
                FileInputStream fileInputStream = context.openFileInput(filenamePass);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                password = (String) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(password == null){
            password ="badPass";
        }
        return password;
    }

    public static void SaveUser(Context context) {
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
