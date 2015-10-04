package com.example.spoti5.ecobussing.Activites;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.ErrorCodes;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Database.IDatabaseConnected;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.R;

import java.util.ArrayList;
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
    TextView error;
    TextView register;

    IUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_screen);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(login);

        emailField = (TextView) findViewById(R.id.emailField);
        passwordField = (TextView) findViewById(R.id.passwordField);
        error = (TextView) findViewById(R.id.login_error);
        register = (TextView) findViewById(R.id.register_label);

        register.setOnClickListener(registerListener);
        passwordField.setOnKeyListener(autoLogin);

        database = DatabaseHolder.getDatabase();

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


        User user;
        ArrayList list = database.getUser();
        System.out.println("got the user");
        System.out.println("size = " + list.size());

       // for(int i = 0; i < list.size(); i++){

       //     System.out.println("printprint");
       // }


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
            }, 5000);

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
    public void addingUserFinished() {
        //never used here
    }

    @Override
    public void loginFinished() {
        switch (database.getErrorCode()){
            case ErrorCodes.NO_ERROR: startOverviewActivity();
                //SaveHandler ska byta till nya user här
                break;
            case ErrorCodes.BAD_EMAIL: error.setText("Ogiltig email");
                break;
            case ErrorCodes.NO_CONNECTION: error.setText("Fel användarnamn eller lösenord");
                break;
            case ErrorCodes.UNKNOWN_ERROR: error.setText("Fel användarnamn eller lösenord");
        }
    }
}
