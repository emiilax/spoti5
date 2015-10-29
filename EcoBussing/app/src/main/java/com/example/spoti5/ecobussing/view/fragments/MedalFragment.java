package com.example.spoti5.ecobussing.view.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.CompanyMedalAdapter;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.GlobalMedalAdapter;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.UserMedalAdapter;

/**
 * Created by Erik on 2015-10-17.
 */
public class MedalFragment extends Fragment {
    private int medalType;
    private ListAdapter listAdapter;

    public static final int USER_MEDALS = 0;
    public static final int COMPANY_MEDALS = 1;
    public static final int GLOBAL_MEDALS = 2;

    public MedalFragment() {

    }

    public static MedalFragment getInstance(int medalType){
        MedalFragment mf = new MedalFragment();

        mf.setMedalType(medalType);

        return mf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medal_list, container, false);

        Activity currentActivity = getActivity();
        ListView listView = (ListView)view.findViewById(R.id.medalList);

        if(this.medalType == USER_MEDALS){
            listAdapter = new UserMedalAdapter(currentActivity);
        }else if(this.medalType == COMPANY_MEDALS){
            listAdapter = new CompanyMedalAdapter(currentActivity);
        }else if(this.medalType == GLOBAL_MEDALS){
            listAdapter = new GlobalMedalAdapter(currentActivity);
        }

        listView.setAdapter(listAdapter);


        return view;
    }


    public void setMedalType(int medalType) {
        this.medalType = medalType;


    }
}
