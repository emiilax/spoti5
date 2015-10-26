package com.example.spoti5.ecobussing.view.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.spoti5.ecobussing.controller.adapters.listadapters.CompanySearchAdapter;
import com.example.spoti5.ecobussing.R;

/**
 * Created by matildahorppu on 09/10/15.
 */
public class ConnectCompanyFragment extends Fragment {

    private EditText searchField;
    private ImageView searchButton;
    private ListView searchResults;

    private CompanySearchAdapter adapter;

    public ConnectCompanyFragment(){
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_connect_company,container,false);

        searchField = (EditText)view.findViewById(R.id.searchField);
        searchButton = (ImageView)view.findViewById(R.id.searchImageView);
        searchResults = (ListView)view.findViewById(R.id.searchResults);

        searchButton.setOnClickListener(search);
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return view;
    }

    private View.OnClickListener search = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            adapter = new CompanySearchAdapter(getContext(), searchField.getText().toString());
            searchResults.setAdapter(adapter);

        }
    };

}
