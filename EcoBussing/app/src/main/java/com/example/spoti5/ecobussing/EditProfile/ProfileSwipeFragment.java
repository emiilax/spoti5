package com.example.spoti5.ecobussing.EditProfile;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spoti5.ecobussing.R;

/**
 * Created by matildahorppu on 09/10/15.
 */
public class ProfileSwipeFragment extends Fragment {

    private ViewPager pager;
    private FragmentStatePagerAdapter adapter;
    private TabLayout tabLayout;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        System.out.println("skapa swipe");
        view = inflater.inflate(R.layout.profile_swipe, container, false);
        System.out.println("skapaaat vyyyyy");

        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        pager = (ViewPager)view.findViewById(R.id.profilePager);
        pager.setAdapter(adapter);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setSelectedTabIndicatorHeight(3);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));

        return view;

    }

}
