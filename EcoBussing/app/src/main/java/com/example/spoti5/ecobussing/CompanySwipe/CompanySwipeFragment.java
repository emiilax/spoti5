package com.example.spoti5.ecobussing.CompanySwipe;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Profiles.Company;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

/**
 * Created by matildahorppu on 09/10/15.
 */
public class CompanySwipeFragment extends Fragment {

    private ViewPager pager;
    private FragmentStatePagerAdapter adapter;
    private TabLayout tabLayout;
    private View view;

    private CharSequence titles[];
    private int nbrTabs;

    private IDatabase database = DatabaseHolder.getDatabase();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profile_swipe, container, false);
        titles = new CharSequence[2];

        initTabInfo();

        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), titles, nbrTabs);
        pager = (ViewPager) view.findViewById(R.id.profilePager);
        pager.setAdapter(adapter);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
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