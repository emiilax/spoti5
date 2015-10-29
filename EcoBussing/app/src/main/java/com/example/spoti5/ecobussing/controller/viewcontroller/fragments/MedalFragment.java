package com.example.spoti5.ecobussing.controller.viewcontroller.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.CompanyMedalAdapter;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.GlobalMedalAdapter;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.UserMedalAdapter;
import com.example.spoti5.ecobussing.controller.adapters.pageradapter.ToplistPagerAdapter;

/**
 * Created by Erik on 2015-10-17.
 */
public class MedalFragment extends Fragment {
    private Activity currentActivity;
    private View view;
    private ListView listView;
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
        view = inflater.inflate(R.layout.medal_list, container, false);

        currentActivity = getActivity();
        listView = (ListView)view.findViewById(R.id.medalList);

        listAdapter = selectMedal();

        listView.setAdapter(listAdapter);


        return view;
    }

    private ListAdapter selectMedal(){
        if(this.medalType == USER_MEDALS){
            return new UserMedalAdapter(currentActivity);
        }else if(this.medalType == COMPANY_MEDALS){
            return new CompanyMedalAdapter(currentActivity);
        }else if(this.medalType == GLOBAL_MEDALS){
            return new GlobalMedalAdapter(currentActivity);
        }
        else{
            return null;
        }
    }


    public void setMedalType(int medalType) {
        this.medalType = medalType;
    }
}
