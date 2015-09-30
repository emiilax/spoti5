package com.example.spoti5.ecobussing.Activites;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Database.IDatabaseConnected;
import com.example.spoti5.ecobussing.Calculations.CheckCreateUserInput;
import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

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
    TextView passwordError;
    TextView inputError;
    TextView login;

    String name;
    String email;
    String password;
    String secondPassword;

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

        passwordError = (TextView) findViewById(R.id.password_error);
        inputError = (TextView) findViewById(R.id.input_error);
        login = (TextView) findViewById(R.id.logInString);

        login.setOnClickListener(goToLogin);
        register_button.setOnClickListener(register);

        secondPasswordView.setOnKeyListener(autoReg);

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

            boolean emailIsOk = CheckCreateUserInput.checkEmail(email);

            if(!emailIsOk){
                inputError.setText("Invalid email");
            } else {
                inputError.setText("");
            }
            if(passIsCorrect && emailIsOk){
                newUser = new User(email, name);
                database.addUser(email, password, newUser, this);
            }
        }
    }

    private boolean valuesIsOk(){
        if(name.equals("") || email.equals("")){
            inputError.setText("All fields must be filled");
            return false;
        } else {
            inputError.setText("");
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
            passwordError.setText("Password must match");
            return false;
        } else {
            int index = CheckCreateUserInput.checkPassword(password);
            switch (index){
                case -1: passwordError.setText("");
                        return true;
                case 0: passwordError.setText("Password to short");
                        return false;
                case 1: passwordError.setText("Password must contain an upper case letter");
                        return false;
                case 2: passwordError.setText("Password must contain an lower case letter");
                        return false;
                case 3: passwordError.setText("Password must contain a number");
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

            if (keyCode == event.KEYCODE_ENTER && !timerRunning) {
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
            }, 5000);


        return true;
        }
    };

    @Override
    public void addingUserFinished() {
        if(database.checkIfCorrectEmail()) {
            database.loginUser(email, password, this);
        } else {
            inputError.setText("Email already exists");
        }
    }

    @Override
    public void loginFinished() {
        if(database.successLogin()){
            SaveHandler.changeUser(newUser);
            startOverviewActivity();
        }
    }
}