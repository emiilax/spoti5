package com.example.spoti5.ecobussing.Activites;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

/**
 * Created by matildahorppu on 12/10/15.
 */
public class EditCompanyFragment extends Fragment {

    IDatabase database;

    public EditCompanyFragment(){

    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = DatabaseHolder.getDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        View view = inflater.inflate(R.layout.fragment_edit_company, container, false);

        //header = (TextView)view.findViewById(R.id.headerTextView);
        //nameTextField = (EditText)view.findViewById(R.id.editTextCompName);
        //passwordTextField = (EditText)view.findViewById(R.id.editTextPassword);
        //saveButton = (Button)view.findViewById(R.id.saveCompButton);
        //saveButton.setOnClickListener(save);

        return view;
    }

}
