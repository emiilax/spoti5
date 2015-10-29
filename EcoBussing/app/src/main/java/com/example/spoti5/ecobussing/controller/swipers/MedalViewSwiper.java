package com.example.spoti5.ecobussing.controller.swipers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.adapters.pageradapter.MedalPagerAdapter;

/**
 * Created by emilaxelsson on 17/10/15.
 */
public class MedalViewSwiper extends Fragment{

    private View view;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_medal_swipe, container, false);

        FragmentStatePagerAdapter mPagerAdapter = new MedalPagerAdapter(getActivity().getSupportFragmentManager());


        mViewPager = (ViewPager)view.findViewById(R.id.pager_medal_view);
        mViewPager.setAdapter(mPagerAdapter);

        fixTabLayout();

        return view;
    }

    public void fixTabLayout(){
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs_medal_view);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorHeight(3);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));
    }



}
