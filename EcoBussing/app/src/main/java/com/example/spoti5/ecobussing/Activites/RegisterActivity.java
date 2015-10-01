package com.example.spoti5.ecobussing.Activites;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Database.UsernameAlreadyExistsException;
import com.example.spoti5.ecobussing.Calculations.CheckCreateUserInput;
import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

/**
 * Created by erikk on 2015-09-23.
 */
public class RegisterActivity extends ActivityController {

    Button register_button;
    EditText nameView;
    EditText usernameView;
    EditText emailView;
    EditText passwordView;
    EditText secondPasswordView;
    TextView passwordError;
    TextView inputError;
    TextView login;

    String name;
    String username;
    String email;
    String password;
    String secondPassword;

    IDatabase database;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Hej");
        setContentView(R.layout.register_screen);

        register_button = (Button) findViewById(R.id.button_register);
        nameView = (EditText) (findViewById(R.id.name));
        usernameView = (EditText) (findViewById(R.id.username));
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
                try{
                    User newUser = new User(username, email, password, name);
                    database.addUser(email, password, newUser);
                    //SaveHandler.changeUser(newUser);
                    //startOverviewActivity();
                } catch (Exception e){
                    inputError.setText("Username already exits");
                }
            }
        }
    }

    private boolean valuesIsOk(){
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
                case 3: passwordError.setText("Password must contain an number");
                        return false;
            }
            return false;
        }
    }

    View.OnKeyListener autoReg = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event){
            if(keyCode == event.KEYCODE_ENTER){
                register();
            }
            return true;
        }
    };


}