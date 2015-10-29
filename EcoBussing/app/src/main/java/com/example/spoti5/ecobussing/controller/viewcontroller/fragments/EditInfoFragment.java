package com.example.spoti5.ecobussing.controller.viewcontroller.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.spoti5.ecobussing.controller.Tools;
import com.example.spoti5.ecobussing.io.CheckUserInput;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.model.ErrorCodes;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabaseConnected;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Erik on 2015-10-04.
 */
public class EditInfoFragment extends Fragment implements IDatabaseConnected {

    private IUser currentUser;
    private EditText nameField;
    private EditText currentPassword;
    private EditText password1;
    private EditText password2;
    private Button saveChanges;
    private ImageView profilePic;
    private IDatabase database;
    private Tools tools;
    private Context context;

    public EditInfoFragment(){}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = super.getContext();
        currentUser = SaveHandler.getCurrentUser();
        View view = inflater.inflate(R.layout.fragment_edit_info, container, false);
        nameField = (EditText) view.findViewById(R.id.nameField);
        password1 = (EditText) view.findViewById(R.id.password1);
        password2 = (EditText) view.findViewById(R.id.password2);
        currentPassword = (EditText) view.findViewById(R.id.current_password);
        saveChanges = (Button) view.findViewById(R.id.save_button);
        profilePic = (ImageView) view.findViewById(R.id.edit_profile_pic);

        profilePic.setOnClickListener(changeImage);
        saveChanges.setOnClickListener(save);
        database = DatabaseHolder.getDatabase();

        password2.setOnKeyListener(autoUpdate);

        tools = Tools.getInstance();
        initFields();
        return view;
    }

    View.OnClickListener changeImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tools.showToast("Ej implementerat",context);
        }
    };

    private void initFields(){
        nameField.setText(currentUser.getName());
    }


    private View.OnClickListener save = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkValues();
        }
    };

    private void checkValues(){
        String pass1 = password1.getText().toString();
        String pass2 = password2.getText().toString();
        String currentPass = currentPassword.getText().toString();
        if(!pass1.equals("")) {
            if (pass1.equals(pass2)) {
                int errorValue = CheckUserInput.checkPassword(pass1);
                switch (errorValue) {
                    case -1:
                        database.changePassword(currentUser.getEmail(), currentPass, pass1, this);
                        changeUser();
                        break;
                    case 0:
                        tools.showToast("Lösenordet är för kort", context);
                        break;
                    case 1:
                        tools.showToast("Det nya lösenordet måste innehålla en stor bokstav", context);
                        break;
                    case 2:
                        tools.showToast("Det nya lösenordet måste innehålla en liten bokstav", context);
                        break;
                }
            } else {
                tools.showToast("Det nya lösenordet matchar inte", context);
            }
        } else {
            changeUser();
        }
    }

    private void changeUser(){
        currentUser.setName(nameField.getText().toString());
        SaveHandler.changeUser(currentUser);
        DatabaseHolder.getDatabase().updateUser(currentUser);
        tools.showToast("Profilen är uppdaterad.", context);
    }

    @Override
    public void addingFinished() {
        //never called
    }

    @Override
    public void loginFinished() {
        CharSequence text="";
        int error = database.getErrorCode();
        if(error == ErrorCodes.NO_CONNECTION){
            text = "Ingen uppkoppling";
        } else if (error == ErrorCodes.WRONG_CREDENTIALS) {
            text ="Ditt nuvarande lösenord stämmer inte";
        } else if(error == ErrorCodes.NO_ERROR){
            text = "Profilen är uppdaterad.";
        }
        tools.showToast(text, context);
    }

    boolean timerRunning = false;
    View.OnKeyListener autoUpdate = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event){
            final Timer t = new Timer();

            if (keyCode == KeyEvent.KEYCODE_ENTER && !timerRunning) {
                checkValues();
            }

            /**
             * Timer, otherwise it calls twice
             */
            timerRunning = true;
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    timerRunning = false;
                    t.cancel();
                }
            }, 1000);

            return true;
        }
    };

}
