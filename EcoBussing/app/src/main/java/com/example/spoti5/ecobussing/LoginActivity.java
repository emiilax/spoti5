package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;

/**
 * Created by erikk on 2015-09-21.
 */
public class LoginActivity extends Activity{

    Button loginButton;
    TextView usernameField;
    TextView passwordField;

    IUser tmpUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(login);

        usernameField = (TextView) findViewById(R.id.usernameField);
        passwordField = (TextView) findViewById(R.id.passwordField);

        tmpUser = new User("Erik", "erik@gmail.com", "hej");
    }

    View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String inputUsername = usernameField.getText().toString();
            String inputPassword = passwordField.getText().toString();
            if(tmpUser.checkPassword(inputPassword) && tmpUser.checkUsername(inputUsername)){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
            } else {
                System.out.println("Incorrect!");
            }

            WifiManager wifiMan = (WifiManager) getSystemService(
                    Context.WIFI_SERVICE);
            WifiInfo wifiInf = wifiMan.getConnectionInfo();
            String macAddr = wifiInf.getBSSID();
            System.out.println(macAddr);
        }
    };
}
