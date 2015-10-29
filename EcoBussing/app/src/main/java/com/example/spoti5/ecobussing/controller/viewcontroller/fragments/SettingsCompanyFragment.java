package com.example.spoti5.ecobussing.controller.viewcontroller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spoti5.ecobussing.controller.viewcontroller.activities.MainActivity;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.model.profile.Company;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matildahorppu on 13/10/15.
 */
public class SettingsCompanyFragment extends Fragment {

    private IDatabase database;
    private IUser currentUser;

    private ImageView companyImage;
    private TextView companyName;
    private TextView employees;
    private TextView co2Saved;
    private TextView connectedUsersText;
    private ListView userList;
    private Button disconnectCompany;

    private List<String> usersConnected;

    public SettingsCompanyFragment(){
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = DatabaseHolder.getDatabase();
        currentUser = SaveHandler.getCurrentUser();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_connected_company,container,false);

        companyImage = (ImageView)view.findViewById(R.id.imageViewComp);
        companyName = (TextView)view.findViewById(R.id.textViewCompanyName);
        employees = (TextView)view.findViewById(R.id.textViewEmployees);
        co2Saved = (TextView)view.findViewById(R.id.textViewCo2);
        connectedUsersText = (TextView)view.findViewById(R.id.textView13);
        userList = (ListView)view.findViewById(R.id.listView);
        disconnectCompany = (Button)view.findViewById(R.id.buttonDisconnect);

        disconnectCompany.setOnClickListener(disconnectFromComp);
        Company company = (Company)database.getCompany(currentUser.getCompany());
        companyName.setText(company.getName());

        employees.setText("Anställda: " + company.getNbrEmployees());
        co2Saved.setText("Sparad co2: " + company.getCO2Saved());
        usersConnected = new ArrayList<>();

        createListView();

        return view;
    }

    private void createListView(){
        List<IUser> tmpList = database.getUsers();
        String company = currentUser.getCompany();

        for(IUser user: tmpList){
            if(user.getCompany().equals(company)){
                usersConnected.add(user.getName());
            }
        }

        userList.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, usersConnected));


    }

    private View.OnClickListener viewCompany = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("Visa företagsprofil");
        }
    };

    private View.OnClickListener disconnectFromComp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Company company = (Company)database.getCompany(currentUser.getCompany());
            company.removeMember(currentUser);
            currentUser.setCompany("");
            database.updateCompany(company);
            database.updateUser(currentUser);

            MainActivity currentActivity = (MainActivity)getActivity();
            currentActivity.updateList(true);
            currentActivity.changeToProfileFragment(currentUser, "Min profil");
        }
    };

}
