package com.example.spoti5.ecobussing.controller.viewcontroller.fragments;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spoti5.ecobussing.controller.adapters.pageradapter.ViewPagerAdapter;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.R;

/**
 * Created by matildahorppu on 09/10/15.
 */
public class CompanySwipeFragment extends Fragment {

    private CharSequence titles[];
    private int nbrTabs;

    private IDatabase database = DatabaseHolder.getDatabase();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_swipe, container, false);
        titles = new CharSequence[2];

        initTabInfo();

        FragmentStatePagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), titles, nbrTabs);
        ViewPager pager = (ViewPager) view.findViewById(R.id.profilePager);
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setSelectedTabIndicatorHeight(3);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));

        return view;

    }

    private void initTabInfo() {
        nbrTabs = 2;
        titles[0] = "Anslut till företag";
        titles[1] = "Skapa företag";

    }

}
