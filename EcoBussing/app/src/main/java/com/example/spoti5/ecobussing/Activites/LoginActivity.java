package com.example.spoti5.ecobussing.Activites;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.ErrorCodes;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Database.IDatabaseConnected;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by erikk on 2015-09-21.
 */
public class LoginActivity extends ActivityController implements IDatabaseConnected{

    Button loginButton;
    TextView emailField;
    TextView passwordField;
    IDatabase database;
    TextView register;

    IUser user;
    private List<IUser> allUsers;
    private List<IUser> topList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_screen);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(login);

        emailField = (TextView) findViewById(R.id.emailField);
        passwordField = (TextView) findViewById(R.id.passwordField);
        register = (TextView) findViewById(R.id.register_label);

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
        String inputEmail = emailField.getText().toString();
        String inputPassword = passwordField.getText().toString();

        database.loginUser(inputEmail, inputPassword, this);

    }

    boolean timerRunning = false;
    View.OnKeyListener autoLogin = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event){
            final Timer t = new Timer();

            if (keyCode == event.KEYCODE_ENTER && !timerRunning) {
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

            /*
            WifiManager wifiMan = (WifiManager) getSystemService(
                    Context.WIFI_SERVICE);
            WifiInfo wifiInf = wifiMan.getConnectionInfo();
            String macAddr = wifiInf.getBSSID();
            System.out.println(macAddr);
            */

            return true;
        }
    };

    @Override
    public void addingFinished() {
        //never used here
    }

    @Override
    public void loginFinished() {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text;
        Toast toast;
        switch (database.getErrorCode()){
            case ErrorCodes.NO_ERROR:
                SaveHandler.changeUser(database.getUser(emailField.getText().toString()));
                startOverviewActivity();

                break;
            case ErrorCodes.BAD_EMAIL: text = "Ogiltig email";
                toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
            case ErrorCodes.NO_CONNECTION:text = "Fel användarnamn eller lösenord";
                toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
            case ErrorCodes.UNKNOWN_ERROR: text = "Fel användarnamn eller lösenord";
                toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
        }
    }

}
