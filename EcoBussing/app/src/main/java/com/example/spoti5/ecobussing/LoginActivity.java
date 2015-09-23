package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.content.Intent;
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

        //usernameField.setOnClickListener(resetField);

        tmpUser = new User("e", "erik@gmail.com", "h");
    }

    View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String inputUsername = usernameField.getText().toString();
            String inputPassword = passwordField.getText().toString();
            if(tmpUser.checkPassword(inputPassword) && tmpUser.checkUsername(inputUsername)){ //must be rewritten
                //Database.getProfileInformationAndStoreSomewhere xD
                finish();
            } else {
                System.out.println("Incorrect!");
            }
        }
    };

   /* View.OnClickListener resetField = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(usernameField.isFocused()){
                System.out.println("Hello1");
                String inputUsername = usernameField.getText().toString();
                if(inputUsername.equals("Username")){
                    usernameField.setText("");
                    System.out.println("Hello2");
                }
            }

            String inputPassword = passwordField.getText().toString();
        }
    };*/
}
