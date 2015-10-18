package com.example.spoti5.ecobussing.CompanySwipe;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Activites.SearchAdapter;
import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Database.SimpelSearch;
import com.example.spoti5.ecobussing.Profiles.Company;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by matildahorppu on 09/10/15.
 */
public class ConnectCompanyFragment extends Fragment {

    private IDatabase database;
    private User currentUser;
    private Company company;

    private AutoCompleteTextView autoCompleteTextView;
    private ImageView searchButton;
    private ImageView compImage;
    private TextView compName;
    private TextView nbrEmployees;
    private TextView nbrConnected;
    private TextView co2Company;
    private TextView compInfo;
    private Button connectButton;

    private ArrayAdapter arrayAdapter;
    private String[] companies;

    private SimpelSearch simpleSearch = SimpelSearch.getInstance();

    public ConnectCompanyFragment(){
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = DatabaseHolder.getDatabase();
        currentUser = (User)SaveHandler.getCurrentUser();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_connect_company,container,false);

        autoCompleteTextView = (AutoCompleteTextView)view.findViewById(R.id.autoCompleteTextView);
        searchButton = (ImageView)view.findViewById(R.id.searchButton);
        compImage = (ImageView)view.findViewById(R.id.imageView7);
        compName = (TextView)view.findViewById(R.id.textViewCompName);
        nbrEmployees = (TextView)view.findViewById(R.id.textViewNbrEmployees);
        nbrConnected = (TextView)view.findViewById(R.id.textViewNbrConnected);
        co2Company = (TextView)view.findViewById(R.id.textViewCompCo2);
        compInfo = (TextView)view.findViewById(R.id.textViewCompInfo);

        connectButton = (Button)view.findViewById(R.id.connectButton);

        searchButton.setOnClickListener(setValues);
        connectButton.setOnClickListener(connectToCompany);
        initSearchObjects();
        autoCompleteTextView.setAdapter(arrayAdapter);

        return view;
    }


    private void initSearchObjects(){

        System.out.println("initSearchObjects");
        ArrayList<IProfile> searchResults = (ArrayList)simpleSearch.search(autoCompleteTextView.getText().toString());
        ArrayList<IProfile> companyResults = new ArrayList<>();

        if(companyResults.size() == 0){
            companies = new String[1];
            companies[0] = "";
        }else {
            for (IProfile profile : searchResults) {
                if (profile instanceof Company) {
                    companyResults.add(profile);
                }
            }
        }

        companies = new String[companyResults.size()];

        for(int i = 0; i < companyResults.size(); i++){
            companies[i] = companyResults.get(i).getName();
        }



        arrayAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1 ,companies);

    }

    private View.OnClickListener setValues = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            company = (Company)database.getCompany(autoCompleteTextView.getText().toString());
            compImage.setVisibility(View.VISIBLE);
            connectButton.setVisibility(View.VISIBLE);

            compName.setText(company.getName());
            nbrEmployees.setText(Integer.toString(company.getNbrEmployees()));
            nbrConnected.setText(Integer.toString(company.getMembers(true).size()));
            co2Company.setText(company.getCO2Saved(false).toString());

            if(!company.getCompanyInfo().equals("")){
                compInfo.setVisibility(View.VISIBLE);
                compInfo.setText(company.getCompanyInfo());
            }

        }
    };

    private View.OnClickListener connectToCompany = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentUser.setCompany(company.getName());
            database.updateUser(currentUser);
            company.addMember(currentUser);
            database.updateCompany(company);
        }
    };

}
