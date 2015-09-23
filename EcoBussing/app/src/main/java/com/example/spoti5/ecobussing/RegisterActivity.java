package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by erikk on 2015-09-23.
 */
public class RegisterActivity extends Activity {

    Button register_button;
    TextView nameView;
    TextView usernameView;
    TextView emailView;
    TextView passwordView;
    TextView secondPasswordView;

    String name;
    String username;
    String email;
    String password;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        register_button = (Button) findViewById(R.id.button_register);
        nameView = (TextView) (findViewById(R.id.name));
        usernameView = (TextView) (findViewById(R.id.username));
        emailView = (TextView) findViewById(R.id.email);

        passwordView = (TextView) findViewById(R.id.first_password);
        secondPasswordView = (TextView) findViewById(R.id.second_password);

        password = passwordView.getText().toString();
        checkPasswords();

    }

    private void checkPasswords(){
        if(!password.equals(secondPasswordView.getText().toString())){
            System.out.println("Amagad");
        }
    }


}