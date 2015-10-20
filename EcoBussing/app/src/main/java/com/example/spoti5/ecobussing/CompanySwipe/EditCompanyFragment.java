package com.example.spoti5.ecobussing.CompanySwipe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Profiles.Company;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;
import com.example.spoti5.ecobussing.UserListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by matildahorppu on 12/10/15.
 */
public class EditCompanyFragment extends Fragment {

    IDatabase database;
    private IUser currentUser;
    private Company usersCompany;

    private ImageView companyPic;
    private TextView companyName;
    private EditText companyEmployees;
    private TextView companyInfoText;
    private EditText companyInfo;
    private TextView connectedUsersText;
    private ExpandableListView userList;
    private Button saveButton;

    private UserListAdapter adapter;

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
        currentUser = SaveHandler.getCurrentUser();
        usersCompany = (Company)database.getCompany(currentUser.getCompany());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        View view = inflater.inflate(R.layout.fragment_edit_company, container, false);

        companyPic = (ImageView)view.findViewById(R.id.companyPic);
        companyName = (TextView)view.findViewById(R.id.companyNameTextField);
        companyEmployees = (EditText)view.findViewById(R.id.employeesEditText);
        companyInfoText = (TextView)view.findViewById(R.id.infoTextField);
        companyInfo = (EditText)view.findViewById(R.id.infoEditText);
        connectedUsersText = (TextView)view.findViewById(R.id.usersTextView);
        userList = (ExpandableListView)view.findViewById(R.id.expandableListView);
        saveButton = (Button)view.findViewById(R.id.saveButton);

        companyName.setText(currentUser.getCompany());
        companyEmployees.setText(Integer.toString(usersCompany.getNbrEmployees()));
        companyInfo.setText(usersCompany.getCompanyInfo());

        adapter = new UserListAdapter(this.getContext());
        userList.setAdapter(adapter);

        saveButton.setOnClickListener(saveCompanyChanges);

        return view;
    }

    private View.OnClickListener saveCompanyChanges = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            usersCompany.setCompanyInfo(companyInfo.getText().toString());
            database.updateCompany(usersCompany);
        }
    };

}
