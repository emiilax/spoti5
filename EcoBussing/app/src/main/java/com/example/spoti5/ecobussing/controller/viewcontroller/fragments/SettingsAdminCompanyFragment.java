package com.example.spoti5.ecobussing.controller.viewcontroller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spoti5.ecobussing.controller.viewcontroller.activities.MainActivity;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.profile.Company;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.UserListAdapter;

import java.util.List;

/**
 * Created by matildahorppu on 12/10/15.
 */
public class SettingsAdminCompanyFragment extends Fragment {

    private IDatabase database;
    private IUser currentUser;
    private Company usersCompany;

    private EditText companyEmployees;
    private EditText companyInfo;
    private UserListAdapter adapter;

    public SettingsAdminCompanyFragment(){

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

        ImageView companyPic = (ImageView)view.findViewById(R.id.companyPic);
        TextView companyName = (TextView)view.findViewById(R.id.companyNameTextField);
        companyEmployees = (EditText)view.findViewById(R.id.employeesEditText);
        TextView companyInfoText = (TextView)view.findViewById(R.id.infoTextField);
        companyInfo = (EditText)view.findViewById(R.id.infoEditText);
        TextView connectedUsersText = (TextView)view.findViewById(R.id.usersTextView);
        ListView userList = (ListView)view.findViewById(R.id.expandableListView);
        Button saveButton = (Button)view.findViewById(R.id.saveButton);

        companyName.setText(currentUser.getCompany());
        companyEmployees.setText(Integer.toString(usersCompany.getNbrEmployees()));
        companyInfo.setText(usersCompany.getCompanyInfo());

        adapter = new UserListAdapter(this.getContext());
        userList.setAdapter(adapter);
        Button removeCompany = (Button)view.findViewById(R.id.remove_company);

        removeCompany.setOnClickListener(companyRemove);

        saveButton.setOnClickListener(saveCompanyChanges);

        return view;
    }

    private View.OnClickListener companyRemove = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentUser.setCompany("");
            SaveHandler.changeUser(currentUser);
            List<String> users = usersCompany.getMembers();
            for(String mail: users){
                IUser user = database.getUser(mail);
                user.setCompany("");
                database.updateUser(user);
            }
            database.removeCompany(usersCompany);
            MainActivity activity = ((MainActivity)getActivity());
            activity.changeToProfileFragment(currentUser, "Min profil");
            activity.updateList(false);

        }
    };

    private View.OnClickListener saveCompanyChanges = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            usersCompany.setCompanyInfo(companyInfo.getText().toString());
            usersCompany.setNbrEmployees(Integer.parseInt(companyEmployees.getText().toString()));
            database.updateCompany(usersCompany);
        }
    };

}
