package com.example.spoti5.ecobussing.SwipeScreens;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spoti5.ecobussing.R;

/**
 * Created by emilaxelsson on 03/10/15.
 */
public class SwipeFragments extends Fragment {

    //

    private FragmentStatePagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_swipe, container, false);
        System.out.println("Create");
        //this will be our swipe/tab view populated with each checkout
        //piece fragment
        mPagerAdapter =
                new PagerAdapter(
                        getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager)view.findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorHeight(3);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));

        //mPagerAdapter.getItem(0);
        //System.out.println(mViewPager.);

        //mViewPager.setCurrentItem();

        return view;
    }

}

