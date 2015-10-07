package com.example.spoti5.ecobussing.SwipeScreens;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;

import com.example.spoti5.ecobussing.BusConnection;
import com.example.spoti5.ecobussing.BusData.Busses;
import com.example.spoti5.ecobussing.R;

import java.io.IOException;

/**
 * Created by emilaxelsson on 03/10/15.
 */
public class SwipeFragments extends Fragment implements View.OnClickListener {

    //

    private FragmentStatePagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Button btnWeek;
    private Button btnMonth;
    private Button btnYear;
    private TabLayout tabLayout;
    private View view;

    private BusConnection busC = new BusConnection();

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_swipe, container, false);
        System.out.println("Create");
        //this will be our swipe/tab view populated with each checkout
        //piece fragment
        mPagerAdapter =
                new PagerAdapter(
                        getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager)view.findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
        
        fixTabLayout();

        // Find buttons
        btnWeek = (Button) view.findViewById(R.id.swipe_btnWeek);
        btnMonth = (Button) view.findViewById(R.id.swipe_btnMonth);
        btnYear = (Button) view.findViewById(R.id.swipe_btnYear);
        
        // Add clicklisteners
        btnWeek.setOnClickListener(this);
        btnMonth.setOnClickListener(this);
        btnYear.setOnClickListener(this);

        btnWeek.performClick();

        return view;
    }
    
    public void fixTabLayout(){
        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorHeight(3);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));
    }

    @Override
    public void onClick(View v) {
        btnWeek.setBackgroundColor(getResources().getColor(R.color.third));
        btnMonth.setBackgroundColor(getResources().getColor(R.color.third));
        btnYear.setBackgroundColor(getResources().getColor(R.color.third));

        if(v == btnWeek){
            btnWeek.setBackgroundColor(getResources().getColor(R.color.secondary));

        }

        if(v == btnMonth){
            btnMonth.setBackgroundColor(getResources().getColor(R.color.secondary));
            try {
                busC.beginJourey(Busses.simulated);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(v == btnYear){
            btnYear.setBackgroundColor(getResources().getColor(R.color.secondary));
            try {
                busC.endJourney(Busses.simulated);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

