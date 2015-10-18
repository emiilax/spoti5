package com.example.spoti5.ecobussing;

import android.app.Activity;
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

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Profiles.Company;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matildahorppu on 13/10/15.
 */
public class ConnectedCompanyFragment extends Fragment {

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

    public ConnectedCompanyFragment(){
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
        companyName.setText(currentUser.getCompany());

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
        }
    };

}
