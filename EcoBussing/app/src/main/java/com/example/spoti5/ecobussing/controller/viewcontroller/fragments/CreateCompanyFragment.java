package com.example.spoti5.ecobussing.controller.viewcontroller.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.spoti5.ecobussing.controller.viewcontroller.activities.MainActivity;
import com.example.spoti5.ecobussing.controller.Tools;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.model.ErrorCodes;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabaseConnected;
import com.example.spoti5.ecobussing.model.profile.Company;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;

/**
 * Created by matildahorppu on 06/10/15.
 */
public class CreateCompanyFragment extends Fragment implements IDatabaseConnected {

    private ImageView compImage;
    private EditText nameTextField;
    private EditText nbrEmployeesTextField;
    private EditText compInfoTextField;
    private Button saveButton;

    private String name;
    private Company newCompany;
    private String compInfo;
    private int nbrEmployees;

    private IUser currentUser;

    private IDatabase database;

    public CreateCompanyFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = DatabaseHolder.getDatabase();
        currentUser = SaveHandler.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.fragment_create_company, container, false);

        compImage = (ImageView)view.findViewById(R.id.imageViewComp);
        //compImage.setOnClickListener(setImage);
        nameTextField = (EditText) view.findViewById(R.id.editTextCompName);
        nbrEmployeesTextField = (EditText) view.findViewById(R.id.editTextEmployees);
        compInfoTextField = (EditText)view.findViewById(R.id.editTextCompInfo);
        saveButton = (Button) view.findViewById(R.id.saveCompButton);
        saveButton.setOnClickListener(save);


        return view;
    }

//    private View.OnClickListener setImage = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Context context = getActivity().getApplicationContext();
//            int duration = Toast.LENGTH_SHORT;
//            CharSequence text = "Bilden kan inte ändras just nu";
//            Toast toast = Toast.makeText(context, text, duration);
//        }
//    };

    private View.OnClickListener save = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Context context = getActivity().getApplicationContext();
            CharSequence text= "";
            if(nameTextField.getText() == null && nbrEmployeesTextField.getText() == null){
                text = "Namn och antal anställda måste anges";
            }else if(nameTextField.getText() == null){
                text = "Namn måste fyllas i";
            }else if(nbrEmployeesTextField.getText() == null){
                text = "Antal anställda måste fyllas i";
            }else {
                registerCompany();
            }
            if(text.equals("")) {
                Tools tools = Tools.getInstance();
                tools.showToast(text, context);
            }
        }
    };


    private void registerCompany() {
        initStrings();
        newCompany = new Company(name, currentUser, nbrEmployees);
        newCompany.setCompanyInfo(compInfo);
        database.addCompany(name, newCompany, this);
        currentUser.setCompany(newCompany.getName());
        SaveHandler.changeUser(currentUser);
        MainActivity currentActivity = (MainActivity)getActivity();
        currentActivity.changeToProfileFragment(newCompany, "Mitt företag");
        currentActivity.updateList(false);
    }


    private void initStrings() {
        name = nameTextField.getText().toString();
        compInfo = compInfoTextField.getText().toString();
        try {
            if (nbrEmployeesTextField.getText() != null) {
                nbrEmployees = Integer.parseInt(nbrEmployeesTextField.getText().toString());
            }
        } catch (NumberFormatException e) {
            nbrEmployees = 0;
        }
    }


    @Override
    public void addingFinished() {
        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text;
        Toast toast;

        if (database.getErrorCode() == ErrorCodes.NO_ERROR) {
            text = "Företaget skapades";
            toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            text = "Företaget finns redan";
            toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Override
    public void loginFinished() {
        System.out.println(database.getErrorCode());
    }
}
