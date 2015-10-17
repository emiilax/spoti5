package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

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

    public static final MedalFragment getInstance(int medalType){
        MedalFragment mf = new MedalFragment();

        mf.setMedalType(medalType);

        return mf;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.medal_list, container, false);

        currentActivity = getActivity();
        listView = (ListView)view.findViewById(R.id.medalList);

        if(this.medalType == USER_MEDALS){
            listAdapter = new UserMedalAdapter();
        }else if(this.medalType == COMPANY_MEDALS){
            listAdapter = new CompanyMedalAdapter();
        }else if(this.medalType == GLOBAL_MEDALS){
            listAdapter = new GlobalMedalAdapter(getActivity());
        }

        listView.setAdapter(listAdapter);


        return view;
    }


    public void setMedalType(int medalType) {
        this.medalType = medalType;


    }
}
