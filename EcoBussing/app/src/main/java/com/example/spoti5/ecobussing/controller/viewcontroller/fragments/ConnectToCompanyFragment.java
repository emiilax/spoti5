package com.example.spoti5.ecobussing.controller.viewcontroller.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.spoti5.ecobussing.controller.viewcontroller.activities.MainActivity;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.CompanySearchAdapter;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IProfile;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by matildahorppu on 09/10/15.
 */
public class ConnectToCompanyFragment extends Fragment {

    private EditText searchField;
    private ImageView searchButton;
    private ListView searchResults;

    private CompanySearchAdapter adapter;

    public ConnectToCompanyFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_connect_company,container,false);

        searchField = (EditText)view.findViewById(R.id.searchField);
        searchButton = (ImageView)view.findViewById(R.id.searchImageView);
        searchResults = (ListView)view.findViewById(R.id.searchResults);

        searchField.setOnKeyListener(autoSearch);
        searchButton.setOnClickListener(search);
        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = adapter.getItem(position);
                IProfile profile = (IProfile) item;
                ((MainActivity)getActivity()).changeToProfileFragment(profile, profile.getName());
            }
        });

        return view;
    }

    boolean timerRunning = false;
    View.OnKeyListener autoSearch = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event){
            final Timer t = new Timer();

            if (keyCode == KeyEvent.KEYCODE_ENTER && !timerRunning) {
                search();
            }

            /**
             * Timer, otherwise it calls the database twice
             */
            timerRunning = true;
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    timerRunning = false;
                    t.cancel();
                }
            }, 1000);
            return true;
        }
    };

    private View.OnClickListener search = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            search();
        }
    };

    private void search(){
        adapter = new CompanySearchAdapter(getContext(), searchField.getText().toString());
        searchResults.setAdapter(adapter);

        closeKeyboard();
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
