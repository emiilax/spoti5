package com.example.spoti5.ecobussing.CompanySwipe;

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
import com.example.spoti5.ecobussing.Profiles.Company;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

/**
 * Created by matildahorppu on 06/10/15.
 */
public class CreateCompanyFragment extends Fragment implements IDatabaseConnected{

    private TextView header;
    private EditText nameTextField;
    private EditText nbrEmployeesTextField;
    private EditText passwordTextField;
    private EditText password2TextField;
    private Button saveButton;

    private String name;
    private String password;
    private Company newCompany;
    private int nbrEmployees;

    private IUser currentUser;

    private IDatabase database;

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
        nbrEmployeesTextField = (EditText)view.findViewById(R.id.editTextEmployees);
        passwordTextField = (EditText)view.findViewById(R.id.editTextPassword);
        password2TextField = (EditText)view.findViewById(R.id.editTextPassword2);
        saveButton = (Button)view.findViewById(R.id.saveCompButton);
        saveButton.setOnClickListener(save);

        return view;
    }

    private View.OnClickListener save = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(checkPassword()) {
                registerCompany();
            }else{
                System.out.println("Lösenorder matchar inte");
            }
        }
    };

    private boolean checkPassword(){
        //Ska vi ha fler krav på företagslösen?
        return passwordTextField.getText().toString().equals(password2TextField.getText().toString());

    }


    private void registerCompany(){
        initStrings();
        System.out.println("In registerCompany()");
        newCompany = new Company(name, (User)currentUser, password, nbrEmployees);
        database.addCompany(name, newCompany, this);

    }


    private void initStrings(){
        name = nameTextField.getText().toString();
        password = passwordTextField.getText().toString();
        nbrEmployees = Integer.parseInt(nbrEmployeesTextField.getText().toString());
    }


    @Override
    public void addingFinished() {
       // System.out.println(newCompany.getMembers(true).get(0).getEmail());
    }

    @Override
    public void loginFinished() {
        System.out.println(database.getErrorCode());
    }
}
