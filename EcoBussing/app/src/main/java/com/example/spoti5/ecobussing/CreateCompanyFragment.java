package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Database.IDatabaseConnected;
import com.example.spoti5.ecobussing.Profiles.BusinessProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

/**
 * Created by matildahorppu on 06/10/15.
 */
public class CreateCompanyFragment extends Fragment implements IDatabaseConnected{

    TextView header;
    EditText nameTextField;
    EditText passwordTextField;
    Button saveButton;

    String name;
    String password;
    BusinessProfile newCompany;

    IUser currentUser;

    IDatabase database;

    public CreateCompanyFragment(){

    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = DatabaseHolder.getDatabase();
        currentUser = SaveHandler.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        View view = inflater.inflate(R.layout.fragment_create_company, container, false);

        header = (TextView)view.findViewById(R.id.headerTextView);
        nameTextField = (EditText)view.findViewById(R.id.editTextCompName);
        passwordTextField = (EditText)view.findViewById(R.id.editTextPassword);
        saveButton = (Button)view.findViewById(R.id.saveCompButton);
        saveButton.setOnClickListener(save);

        return view;
    }

    private View.OnClickListener save = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            registerCompany();
        }
    };


    private void registerCompany(){
        initStrings();

        newCompany = new BusinessProfile(name, (User)currentUser);
        database.addCompany(name, password, newCompany, this);

    }


    private void initStrings(){
        name = nameTextField.getText().toString();
        password = passwordTextField.getText().toString();
    }


    @Override
    public void addingUserFinished() {
        System.out.println(database.getErrorCode());
    }

    @Override
    public void loginFinished() {
        System.out.println(database.getErrorCode());
    }
}
