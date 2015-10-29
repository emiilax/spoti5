package com.example.spoti5.ecobussing.Activites;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.spoti5.ecobussing.controller.Tools;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.model.ErrorCodes;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabaseConnected;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by erikk on 2015-09-21.
 */
public class LoginActivity extends ActivityController implements IDatabaseConnected{

    private TextView emailField;
    private TextView passwordField;
    private IDatabase database;
    private Tools tools;
    private IUser user;
    private List<IUser> allUsers;
    private List<IUser> topList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_screen);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(login);
        tools = Tools.getInstance();

        emailField = (TextView) findViewById(R.id.emailField);
        passwordField = (TextView) findViewById(R.id.passwordField);
        TextView register = (TextView) findViewById(R.id.register_label);

        register.setOnClickListener(registerListener);
        passwordField.setOnKeyListener(autoLogin);

        database = DatabaseHolder.getDatabase();

        List<IUser> users = database.getUserToplistMonth();

        for(IUser u: users){
            System.out.println(u.getCo2CurrentMonth());
        }

    }

    View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            login();
        }
    };

    View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startRegisterActivity();
        }
    };

    private void login(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        String inputEmail = emailField.getText().toString();
        String inputPassword = passwordField.getText().toString();

        database.loginUser(inputEmail, inputPassword, this);

    }

    boolean timerRunning = false;
    View.OnKeyListener autoLogin = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event){
            final Timer t = new Timer();

            if (keyCode == KeyEvent.KEYCODE_ENTER && !timerRunning) {
                login();
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
        //never used here
    }

    @Override
    public void loginFinished() {
        CharSequence text = "";
        switch (database.getErrorCode()){
            case ErrorCodes.NO_ERROR:
                SaveHandler.changeUser(database.getUser(emailField.getText().toString()));
                startOverviewActivity();
                break;
            case ErrorCodes.BAD_EMAIL: text = "Ogiltig email";
                break;
            case ErrorCodes.WRONG_CREDENTIALS:text = "Fel användarnamn eller lösenord";
                break;
            case ErrorCodes.NO_CONNECTION:text = "Ingen uppkoppling";
                break;
            case ErrorCodes.UNKNOWN_ERROR: text = "Fel användarnamn eller lösenord";
                break;
        }
        if(text.length() != 0) {
            tools.showToast(text, context);
        }
    }

}
