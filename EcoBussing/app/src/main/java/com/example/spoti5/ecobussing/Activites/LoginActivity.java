package com.example.spoti5.ecobussing.Activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

/**
 * Created by erikk on 2015-09-21.
 */
public class LoginActivity extends ActivityController{

    Button loginButton;
    TextView usernameField;
    TextView passwordField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(login);

        usernameField = (TextView) findViewById(R.id.usernameField);
        passwordField = (TextView) findViewById(R.id.passwordField);

        passwordField.setOnKeyListener(autoLogin);
    }

    View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            login();
        }
    };

    private void login(){
        String inputUsername = usernameField.getText().toString();
        String inputPassword = passwordField.getText().toString();
        if(SaveHandler.getCurrentUser().checkPassword(inputPassword) &&
                SaveHandler.getCurrentUser().checkUsername(inputUsername)){ //must be rewritten
            startOverviewActivity();
        } else {
            System.out.println("Incorrect!");
        }
    }

    View.OnKeyListener autoLogin = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event){
            if(keyCode == event.KEYCODE_ENTER){
                login();
            }

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
}
