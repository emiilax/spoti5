package com.example.spoti5.ecobussing.Activites;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.model.ErrorCodes;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabaseConnected;
import com.example.spoti5.ecobussing.controller.calculations.CheckUserInput;
import com.example.spoti5.ecobussing.model.profile.User;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by erikk on 2015-09-23.
 */
public class RegisterActivity extends ActivityController implements IDatabaseConnected{

    Button register_button;
    EditText nameView;
    EditText emailView;
    EditText passwordView;
    EditText secondPasswordView;
    TextView login;

    String name;
    String email;
    String password;
    String secondPassword;

    private Context context;
    private int duration = Toast.LENGTH_SHORT;
    private CharSequence toastText;

    IDatabase database;
    User newUser;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        register_button = (Button) findViewById(R.id.button_register);
        nameView = (EditText) (findViewById(R.id.name));
        emailView = (EditText) findViewById(R.id.email);

        passwordView = (EditText) findViewById(R.id.first_password);
        secondPasswordView = (EditText) findViewById(R.id.second_password);
        login = (TextView) findViewById(R.id.logInString);

        login.setOnClickListener(goToLogin);
        register_button.setOnClickListener(register);

        secondPasswordView.setOnKeyListener(autoReg);
        context = getApplicationContext();
    }

    View.OnClickListener goToLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startLoginActivity();
        }
    };

    View.OnClickListener register = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            register();
        }
    };

    private void register(){
        getDatabase();
        initStrings();
        boolean passIsCorrect = checkPasswords();

        if(valuesIsOk()){

            boolean emailIsOk = CheckUserInput.checkEmail(email);

            if(!emailIsOk){
                toastText = "Ogiltig email";
                showToast();
            }

            if(passIsCorrect && emailIsOk){
                newUser = new User(email, name);
                database.addUser(email, password, newUser, this);
            }
        }
    }

    private boolean valuesIsOk(){
        if(name.equals("") || email.equals("")){
            toastText = "Alla fält måste fyllas i";
            showToast();
            return false;
        } else {
            return true;
        }
    }

    private void getDatabase(){
        database = DatabaseHolder.getDatabase();
    }

    private void initStrings(){
        name = nameView.getText().toString();
        email = emailView.getText().toString();
        password = passwordView.getText().toString();
        secondPassword = secondPasswordView.getText().toString();
    }

    private boolean checkPasswords(){
        if(!password.equals(secondPassword)){
            toastText = "Lösenorden måste matcha";
            showToast();
            return false;
        } else {
            int index = CheckUserInput.checkPassword(password);
            switch (index){
                case -1:
                        return true;
                case 0: toastText = "Lösenordet är för kort";
                    showToast();
                        return false;
                case 1: toastText = "Lösenordet måste innehålla en stor bokstav";
                    showToast();
                        return false;
                case 2: toastText = "Lösenordet måste innehålla en liten bokstav";
                    showToast();
                        return false;
                case 3: toastText = "Lösenordet måste innehålla en siffra";
                    showToast();
                        return false;
            }
            return false;
        }
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

        switch (database.getErrorCode()){
            case ErrorCodes.NO_ERROR: database.loginUser(email, password, this);
                SaveHandler.changeUser(newUser);
                break;
            case ErrorCodes.BAD_EMAIL: toastText ="Mailen är ogiltig";
                showToast();
                break;
            case ErrorCodes.EMAIL_ALREADY_EXISTS: toastText = "Mailen finns redan";
                showToast();
                break;
            case ErrorCodes.NO_CONNECTION: toastText = "Ingen uppkoppling";
                showToast();
                break;
            case ErrorCodes.UNKNOWN_ERROR: toastText = "något gick fel";
                showToast();
                break;
        }
    }

    @Override
    public void loginFinished() {
        switch (database.getErrorCode()){
            case ErrorCodes.NO_ERROR: startOverviewActivity();
                SaveHandler.changeUser(newUser);
                break;
            case ErrorCodes.NO_CONNECTION: toastText = "Registrering lyckades, men uppkopplingen försvann. Försök logga in.";
                showToast();
                break;
            case ErrorCodes.UNKNOWN_ERROR: toastText = "Något gick fel.";
                showToast();
                break;
        }
    }

    private void showToast(){
        Toast toast = Toast.makeText(context,toastText, duration);
        toast.show();
    }
}