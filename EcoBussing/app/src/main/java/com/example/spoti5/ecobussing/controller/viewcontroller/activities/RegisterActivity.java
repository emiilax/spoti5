package com.example.spoti5.ecobussing.controller.viewcontroller.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.spoti5.ecobussing.controller.Tools;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.model.ErrorCodes;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabaseConnected;
import com.example.spoti5.ecobussing.io.CheckUserInput;
import com.example.spoti5.ecobussing.controller.profile.User;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by erikk on 2015-09-23.
 */
public class RegisterActivity extends ActivityController implements IDatabaseConnected{

    private EditText nameView;
    private EditText emailView;
    private EditText passwordView;
    private EditText secondPasswordView;
    private Tools tools;

    private String name;
    private String email;
    private String password;
    private String secondPassword;

    private Context context;
    private IDatabase database;
    private User newUser;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        Button register_button = (Button) findViewById(R.id.button_register);
        nameView = (EditText) (findViewById(R.id.name));
        emailView = (EditText) findViewById(R.id.email);

        passwordView = (EditText) findViewById(R.id.first_password);
        secondPasswordView = (EditText) findViewById(R.id.second_password);
        TextView login = (TextView) findViewById(R.id.logInString);

        login.setOnClickListener(goToLogin);
        register_button.setOnClickListener(register);
        tools = Tools.getInstance();
        secondPasswordView.setOnKeyListener(autoReg);
        context = getApplicationContext();
    }

    private View.OnClickListener goToLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startLoginActivity();
        }
    };

    private View.OnClickListener register = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            register();
        }
    };

    //Checks input values, then sends data to the database and adds user, if the email isn't taken
    private void register(){
        getDatabase();
        initStrings();
        boolean passIsCorrect = checkPasswords();

        if(valuesIsOk()){
            boolean emailIsOk = CheckUserInput.checkEmail(email);
            if(!emailIsOk){
                tools.showToast("Ogiltig email", context);
            }

            if(passIsCorrect && emailIsOk){
                newUser = new User(email, name);
                database.addUser(email, password, newUser, this);
            }
        }
    }

    //checks if textfields have anything in them
    private boolean valuesIsOk(){
        if(name.equals("") || email.equals("")){
            tools.showToast("Alla fält måste fyllas i", context);
            return false;
        } else {
            return true;
        }
    }

    private void getDatabase(){
        database = DatabaseHolder.getDatabase();
    }

    //sets all strings to the input in the textfield
    private void initStrings(){
        name = nameView.getText().toString();
        email = emailView.getText().toString();
        password = passwordView.getText().toString();
        secondPassword = secondPasswordView.getText().toString();
    }

    //simple password check, shows toast if anything is wrong
    private boolean checkPasswords(){
        CharSequence toastText = "";
        if(!password.equals(secondPassword)){
            toastText = "Lösenorden måste matcha";
            return false;
        } else {
            int index = CheckUserInput.checkPassword(password);
            switch (index) {
                case -1:
                    return true;
                case 0:
                    toastText = "Lösenordet är för kort";
                    return false;
                case 1:
                    toastText = "Lösenordet måste innehålla en stor bokstav";
                    return false;
                case 2:
                    toastText = "Lösenordet måste innehålla en liten bokstav";
                    return false;
                case 3:
                    toastText = "Lösenordet måste innehålla en siffra";
                    return false;
            }
        }

        tools.showToast(toastText, context);
        return false;
    }

    boolean timerRunning = false;
    private View.OnKeyListener autoReg = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            final Timer t = new Timer();

            if (keyCode == KeyEvent.KEYCODE_ENTER && !timerRunning) {
                register();
            }

            /**
             * Timer, otherwise it calls the database twice
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

    @Override
    public void addingFinished() {
        CharSequence toastText ="";
        switch (database.getErrorCode()){
            case ErrorCodes.NO_ERROR: database.loginUser(email, password, this);
                break;
            case ErrorCodes.BAD_EMAIL: toastText ="Mailen är ogiltig";
                break;
            case ErrorCodes.EMAIL_ALREADY_EXISTS: toastText = "Mailen finns redan";
                break;
            case ErrorCodes.NO_CONNECTION: toastText = "Ingen uppkoppling";
                break;
            case ErrorCodes.UNKNOWN_ERROR: toastText = "något gick fel";
                break;
        }
        if(toastText.length() != 0){
            tools.showToast(toastText, context);
        }
    }

    @Override
    public void loginFinished() {
        CharSequence toastText = "";
        switch (database.getErrorCode()){
            case ErrorCodes.NO_ERROR: startOverviewActivity();
                SaveHandler.changeUser(newUser);
                break;
            case ErrorCodes.NO_CONNECTION: toastText = "Registrering lyckades, men uppkopplingen försvann. Försök logga in.";
                break;
            case ErrorCodes.UNKNOWN_ERROR: toastText = "Något gick fel.";
                break;
        }
        if(toastText.length() != 0){
            tools.showToast(toastText, context);
        }
    }

}