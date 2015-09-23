package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Database.TmpDatabase;
import com.example.spoti5.ecobussing.Profiles.CheckValues;

import org.w3c.dom.Text;

/**
 * Created by erikk on 2015-09-23.
 */
public class RegisterActivity extends Activity {

    Button register_button;
    EditText nameView;
    EditText usernameView;
    EditText emailView;
    EditText passwordView;
    EditText secondPasswordView;
    TextView passwordError;
    TextView inputError;

    String name;
    String username;
    String email;
    String password;
    String secondPassword;

    IDatabase database;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        register_button = (Button) findViewById(R.id.button_register);
        nameView = (EditText) (findViewById(R.id.name));
        usernameView = (EditText) (findViewById(R.id.username));
        emailView = (EditText) findViewById(R.id.email);

        passwordView = (EditText) findViewById(R.id.first_password);
        secondPasswordView = (EditText) findViewById(R.id.second_password);

        passwordError = (TextView) findViewById(R.id.password_error);
        inputError = (TextView) findViewById(R.id.input_error);

        register_button.setOnClickListener(register);

    }

    View.OnClickListener register = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDatabase();
            initStrings();
            boolean passIsCorrect = checkPasswords();

            if(valuesNotNull()){
                boolean usernameExists = database.usernameExists(username);
                if(checkPasswords()){

                }
            }

        }
    };

    private boolean valuesNotNull(){
        if(name.equals("") || username.equals("") || email.equals("")){
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
        username = usernameView.getText().toString();
        email = emailView.getText().toString();
        password = passwordView.getText().toString();
        secondPassword = secondPasswordView.getText().toString();
    }

    private boolean checkPasswords(){
        if(!password.equals(secondPassword)){
            passwordError.setText("Password must match");
            return false;
        } else {
            int index = CheckValues.checkPassword(password);
            switch (index){
                case -1: passwordError.setText("");
                        return true;
                case 0: passwordError.setText("Password to short");
                        return false;
                case 1: passwordError.setText("Password must contain an upper case letter");
                        return false;
                case 2: passwordError.setText("Password must contain an lower case letter");
                        return false;
                case 3: passwordError.setText("Password must contain an number");
                        return false;
            }
            return false;
        }
    }


}