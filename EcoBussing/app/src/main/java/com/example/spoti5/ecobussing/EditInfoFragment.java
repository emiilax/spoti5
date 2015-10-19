package com.example.spoti5.ecobussing;

import android.app.Activity;
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
import android.widget.Toast;

import com.example.spoti5.ecobussing.Calculations.CheckUserInput;
import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.ErrorCodes;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Database.IDatabaseConnected;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

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
    private Context context;
    private ImageView profilePic;
    private IDatabase database;

    public EditInfoFragment(){

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        initFields();
        return view;
    }

    View.OnClickListener changeImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CharSequence text = "Ej implementerat";
            showToast(text);
        }
    };

    private void showToast(CharSequence text){
        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

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
                        showToast("Lösenordet är för kort");
                        break;
                    case 1:
                        showToast("Det nya lösenordet måste innehålla en stor bokstav");
                        break;
                    case 2:
                        showToast("Det nya lösenordet måste innehålla en liten bokstav");
                        break;
                }
            } else {
                showToast("Det nya lösenordet matchar inte");
            }
        } else {
            changeUser();
        }
    }

    private void changeUser(){
        currentUser.setName(nameField.getText().toString());
        SaveHandler.changeUser(currentUser);
        DatabaseHolder.getDatabase().updateUser(currentUser);

        CharSequence text = "Profilen är uppdaterad.";
        showToast(text);
    }

    @Override
    public void addingFinished() {
        //never called
    }

    @Override
    public void loginFinished() {
        CharSequence text;
        int error = database.getErrorCode();
        if(error == ErrorCodes.NO_CONNECTION){
            text = "Ingen uppkoppling";
            showToast(text);
        } else if (error == ErrorCodes.WRONG_CREDENTIALS) {
            text ="Ditt nuvarande lösenord stämmer inte";
            showToast(text);
        } else if(error == ErrorCodes.NO_ERROR){
            text = "Profilen är uppdaterad.";
            showToast(text);
        }
    }

    boolean timerRunning = false;
    View.OnKeyListener autoUpdate = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event){
            final Timer t = new Timer();

            if (keyCode == event.KEYCODE_ENTER && !timerRunning) {
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
